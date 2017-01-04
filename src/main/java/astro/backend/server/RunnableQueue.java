package astro.backend.server;

import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class RunnableQueue implements Runnable {

    private final BlockingQueue<Event> actionQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executor;
    private final EventDispatcher dispatcher;

    private boolean running = false;



    RunnableQueue(EventDispatcher dispatcher, ExecutorService executor) {
        this.dispatcher = dispatcher;
        this.executor = executor;
    }

    @Override
    public void run() {

        Event event;
        try {
            event = actionQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("queue error", e);
        }
        dispatcher.dispatch(event);

    }

    public boolean add(Event event) {
        return addImpl(event);
    }

    private synchronized boolean addImpl(Event event) {
        boolean result = actionQueue.add(event);
        if (!running) {
            executor.execute(this);
            running = true;
        }
        return result;
    }



}
