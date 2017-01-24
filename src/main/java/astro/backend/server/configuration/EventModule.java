package astro.backend.server.configuration;

import astro.backend.server.ActionQueue;
import astro.backend.server.event.action.DataRequestEvent;
import astro.backend.server.event.action.DataRequestHandler;
import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.shiro.concurrent.SubjectAwareExecutorService;

import java.util.concurrent.ExecutorService;

public class EventModule extends AbstractModule{

    private ActionQueue actionQueue;
    private EventDispatcher dispatcher;

    @Override
    protected void configure() {
        dispatcher = new EventDispatcher();

    }

    @Provides
    public EventDispatcher provideDispatcher(){
        return dispatcher;
    }


    @Provides
    public ActionQueue provideActionQueue(ExecutorService executorService){
        if(actionQueue == null){
            actionQueue = new ActionQueue(dispatcher, executorService);
        }

        return actionQueue;
    }
}
