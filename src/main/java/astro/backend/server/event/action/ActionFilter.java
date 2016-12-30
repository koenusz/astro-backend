package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import netty.Player;

@Getter
public class ActionFilter<T extends Event> {


    private Class<T> type;

    private ObjectMapper mapper;

    public ActionFilter(Class<T> type, ObjectMapper objectMapper) {
        this.type = type;
        this.mapper = objectMapper;
    }

    public boolean identify(String actiontype) {
        if (actiontype.equalsIgnoreCase(type.getSimpleName())) {
            return true;
        } else if (actiontype.equalsIgnoreCase(type.getSimpleName().replace("Action", ""))) {
            return true;
        } else {
            return false;
        }
    }

    public T filter(ActionEvent actionEvent) {
        try {
            return mapper.treeToValue(actionEvent.getData(), type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during deserialization", e);
        }
    }
}
