package astro.backend.server.configuration;

import astro.backend.server.ActionQueue;
import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.concurrent.ExecutorService;

public class EventModule extends AbstractModule{

    private ActionQueue actionQueue;
    private EventDispatcher dispatcher;

    @Override
    protected void configure() {
        dispatcher = new EventDispatcher();
        actionQueue = new ActionQueue();
    }

    @Provides
    public EventDispatcher provideDispatcher(){
        return dispatcher;
    }

    @Provides
    public ActionQueue provideActionQueue(ExecutorService executorService){
        return actionQueue;
    }
}
