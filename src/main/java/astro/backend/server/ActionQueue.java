package astro.backend.server;

import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.Inject;

import java.util.concurrent.ExecutorService;

public class ActionQueue extends RunnableQueue {

    @Inject
    public ActionQueue(EventDispatcher dispatcher, ExecutorService executor) {
        super(dispatcher, executor);
    }
}
