package netty;

import artemis.component.Position;
import artemis.component.Subscription;
import astro.backend.server.GameServer;
import com.artemis.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.Map;
import javaslang.collection.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SerialisationTest {

    private static final Logger logger = LogManager.getLogger();
    private Injector injector;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initThis(){
        injector = GameServer.initInjector();
        objectMapper = injector.getInstance(ObjectMapper.class);
    }

    @Test
    public void serialiseResponse() throws JsonProcessingException {



        Position position = new Position();
        position.x = 1;
        position.y = 1;

        Subscription subscription = new Subscription();
        subscription.updateFrontend = true;

        Map<String, Component> components = HashMap.of("Subscription", subscription);

        Response response = new Response(HashMap.of(new Tuple2<>(1, components)));

        logger.debug(objectMapper.writer().writeValueAsBytes(response));

        logger.debug(objectMapper.writer().writeValueAsString(response));

    }


}
