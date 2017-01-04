package astro.backend.server;

import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.Inject;

import java.util.concurrent.ExecutorService;

public class InstantSystemQueue extends RunnableQueue{

    @Inject
    public InstantSystemQueue(EventDispatcher dispatcher, ExecutorService executor) {
        super(dispatcher, executor);
    }
}
