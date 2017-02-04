package astro.backend.server.configuration;

import astro.backend.server.ActionQueue;
import astro.backend.server.event.frame.EventDispatcher;
import com.artemis.World;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import netty.Responder;
import netty.ResponderImpl;

import java.util.concurrent.ExecutorService;

public class EventModule extends AbstractModule{

    private ActionQueue actionQueue;
    private EventDispatcher dispatcher;
    private ResponderImpl responderImpl;

    @Override
    protected void configure() {
        dispatcher = new EventDispatcher();
        actionQueue = new ActionQueue();

        // @Fixme does this belong here?
        bind(Responder.class).to(ResponderImpl.class);
    }

    @Provides
    public ResponderImpl provideResponseBuilder(World world){
        if(responderImpl == null){
            responderImpl = new ResponderImpl(world);
        }

        return responderImpl;
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
