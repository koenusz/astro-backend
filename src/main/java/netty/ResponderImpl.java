package netty;

import astro.backend.server.event.action.DataRequestEvent;
import astro.backend.server.event.frame.Event;
import com.artemis.Component;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.google.inject.Inject;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.control.Option;

public class ResponderImpl implements Responder {

    //There are two queues to make sure you do not pass the same event twice in an update.


    @Inject
    public ResponderImpl(World world) {
        this.world = world;
    }

    private World world;

    private Queue<Event> leftEventQueue = Queue.empty();

    private Queue<Event> rightEventQueue = Queue.empty();

    private boolean left = true;

    public void enqueue(Event event) {
        if (left) {
            leftEventQueue = leftEventQueue.enqueue(event);
        } else {
            rightEventQueue = rightEventQueue.enqueue(event);
        }
    }

    public void enqueueAll(Iterable<Event> event) {
        event.forEach(this::enqueue);
    }

    private boolean nonEmpty() {
        if (left) {
            return leftEventQueue.nonEmpty();
        } else {
            return rightEventQueue.nonEmpty();
        }
    }

    private Event dequeue() {
        if (left) {
            Tuple2<Event, Queue<Event>> dequeued = leftEventQueue.dequeue();
            leftEventQueue = dequeued._2;
            return dequeued._1;
        } else {
            Tuple2<Event, Queue<Event>> dequeued = rightEventQueue.dequeue();
            rightEventQueue = dequeued._2;
            return dequeued._1;
        }
    }


    @Override
    public void respond(Map<Player, Set<Integer>> responseEntities) {

        List<Event> notPresent = List.empty();
        while (nonEmpty()) {
            Event currentEvent = dequeue();
            Player player = currentEvent.getPlayer();
            Option<Set<Integer>> entities = responseEntities.get(player);


            if (entities.isDefined()) {

                Map<Integer, Map<String, Component>> componentMap = HashMap.empty();
                for (int entity : entities.get()) {

                    Bag<Component> components = new Bag<>();
                    world.getComponentManager().getComponentsFor(entity, components);

                    HashMap<String, Component> translated = HashMap.empty();

                    List<String> requestedComponents = List.empty();
                    if(currentEvent instanceof DataRequestEvent) {
                        // TODO perhaps create an interface for the getComponents method
                       requestedComponents = ((DataRequestEvent) currentEvent).getComponents();
                    }

                    for (Component component : components) {

                        if (requestedComponents.contains(component.getClass().getSimpleName())) {
                            translated = translated.put(component.getClass().getSimpleName(), component);
                        }
                    }
                    componentMap = componentMap.put(entity, translated);
                }
                Response response = new Response(componentMap);
                currentEvent.getPlayer().send(response);
            } else {
                notPresent = notPresent.append(currentEvent);
            }
        }
        left = !left;
        enqueueAll(notPresent);
    }
}
