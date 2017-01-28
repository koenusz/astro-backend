package astro.backend.server.event.action;

import artemis.EntitySubscriberSystem;
import astro.backend.server.event.frame.Handler;
import com.artemis.World;
import com.google.inject.Inject;
import javaslang.collection.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataRequestHandler implements Handler<DataRequestEvent> {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private World world;

    @Override
    public void onEvent(DataRequestEvent event) {
        world.getSystem(EntitySubscriberSystem.class)
                .subscribe(event.getPlayer(), HashSet.of(event.getEntityId()));
    }
}
