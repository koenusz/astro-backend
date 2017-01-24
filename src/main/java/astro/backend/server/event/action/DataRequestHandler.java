package astro.backend.server.event.action;

import astro.backend.server.datastore.DoaFinder;
import astro.backend.server.datastore.entity.EntityDoa;
import astro.backend.server.engine.Component;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Entity;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.Handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataRequestHandler implements Handler {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private Engine engine;

    @Inject
    private DoaFinder doaFinder;
    @Inject
    private EntityDoa entityDoa;
    @Inject
    private ObjectMapper mapper;

    @Override
    public void onEvent(Event event) {
        if (event instanceof DataRequestEvent) {

            DataRequestEvent dataRequestEvent = (DataRequestEvent) event;
            Optional<Entity> entity = engine.findEntity(dataRequestEvent.getEntityId());
            if (entity.isPresent()) {
                List<Component> requestedComponents = collectComponents(entity.get(), dataRequestEvent);
                logger.info("found components {}", requestedComponents);
                try {
                    dataRequestEvent.getPlayer().getChannel().writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(requestedComponents)));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //@TODO handle null case properly
                throw new RuntimeException("TODO: handle not found entity properly");
            }
        }

    }

    private List<Component> collectComponents(Entity entity, DataRequestEvent dataRequestEvent) {

        List<String> components = dataRequestEvent.getComponents().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return entity.getComponents()
                .stream()
                .filter(com -> components.contains(com.getClass().getInterfaces()[0].getSimpleName().toLowerCase()))
                .collect(Collectors.toList());
    }
}
