package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EventDeserializer extends JsonDeserializer<Event> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Event deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = mapper.readTree(jp);
            /*write you own condition*/
        if (root.has("type")){

            switch (root.get("type").asText()) {
                case "simulator":
                    return mapper.treeToValue(root.get("data"), SimulatorEvent.class);
                case "datarequest":

                   // JsonNode data = root.get("data");
                    return mapper.treeToValue(root.get("data"), DataRequestEvent.class);
                default:
                    logger.error("no deserializer for {} ", root.get("type").asText());
                    return null;
            }

        }
        logger.error("the message recieved has no type field");
        return null;
    }
}
