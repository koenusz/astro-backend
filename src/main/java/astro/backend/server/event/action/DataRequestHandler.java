package astro.backend.server.event.action;

import astro.backend.server.engine.Component;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Entity;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.Handler;
import com.google.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class DataRequestHandler implements Handler {

    @Inject
    private Engine engine;

    @Override
    public void onEvent(Event event) {
        if (event instanceof DataRequestEvent) {

            DataRequestEvent dre = (DataRequestEvent) event;
            Entity entity = engine.findEntity(dre.getEntityId());
            List<Component> requestedComponents = entity.getComponents()
                    .stream()
                    .filter(com -> dre.getComponents().contains(com.getClass().getSimpleName()))
                    .collect(Collectors.toList());

        }

    }
}
