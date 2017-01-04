package astro.backend.server.event.action;

import astro.backend.server.engine.Component;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Entity;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.Handler;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class DataRequestHandler implements Handler {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private Engine engine;

    @Override
    public void onEvent(Event event) {
        if (event instanceof DataRequestEvent) {

            DataRequestEvent dataRequestEvent = (DataRequestEvent) event;
            Entity entity = engine.findEntity(dataRequestEvent.getEntityId());
            List<Component> requestedComponents = entity.getComponents()
                    .stream()
                    .filter(com -> dataRequestEvent.getComponents().contains(com.getClass().getSimpleName()))
                    .collect(Collectors.toList());
            logger.debug("found components {}", requestedComponents);
        }

    }
}
