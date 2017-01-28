package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import netty.Player;

@Getter
public class ActionEvent implements Event {

    private final String actionType;
    private final JsonNode data;
    @JsonCreator
    protected ActionEvent(@JsonProperty("type") String type
            , @JsonProperty("data") JsonNode data) {
        this.actionType = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ActionEvent{" +
                "actionType='" + actionType + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public void assignToPlayer(Player player) {

    }
}
