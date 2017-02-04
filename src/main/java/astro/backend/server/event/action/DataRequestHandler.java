package astro.backend.server.event.action;

import artemis.EntitySubscriberSystem;
import artemis.component.Subscription;
import astro.backend.server.event.frame.Handler;
import com.artemis.World;
import com.google.inject.Inject;
import javaslang.collection.HashSet;
import netty.ResponderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataRequestHandler implements Handler<DataRequestEvent> {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private World world;

    @Inject
    private ResponderImpl responderImpl;

    @Override
    public void onEvent(DataRequestEvent event) {
        responderImpl.enqueue(event);
        world.getEntity(event.getEntityId()).getComponent(Subscription.class).updateFrontend = true;
        world.getSystem(EntitySubscriberSystem.class)
                .subscribe(event.getPlayer(), HashSet.of(event.getEntityId()));
    }
}
