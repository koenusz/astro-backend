package astro.backend.server;

import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.Inject;
import javaslang.Tuple2;
import javaslang.collection.Queue;

import java.util.concurrent.ExecutorService;

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
