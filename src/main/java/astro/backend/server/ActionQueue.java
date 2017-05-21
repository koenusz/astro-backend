package astro.backend.server;

import astro.backend.server.event.frame.Event;
import io.vavr.Tuple2;
import io.vavr.collection.Queue;

public class ActionQueue {

    private Queue<Event> queue;

    public ActionQueue() {
        queue = Queue.empty();
    }

    public void enqueue(Event event){
        queue = queue.enqueue(event);
    }

    public Event dequeue(){
        Tuple2<Event, Queue<Event>> dequeued = queue.dequeue();
        queue = dequeued._2();
        return dequeued._1();
    }

    public boolean nonEmpty(){
        return queue.nonEmpty();
    }
}
