package astro.backend.server;

import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.Inject;

import java.util.concurrent.ExecutorService;

public class TurnSystemQueue extends RunnableQueue {

    @Inject
    public TurnSystemQueue(EventDispatcher dispatcher, ExecutorService executor) {
        super(dispatcher, executor);
    }
}
