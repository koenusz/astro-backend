package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import netty.Player;

import java.util.List;

@Getter
public class DataRequestEvent implements Event {

    private String entityType;
    private List<String> components;
    private long entityId;
    private Player player;

    @JsonCreator
    public DataRequestEvent(@JsonProperty("entityType") String entityType
            , @JsonProperty("components") List<String> components
            , @JsonProperty("entityId") long entityId) {
        this.entityType = entityType;
        this.components = components;
        this.entityId = entityId;
    }

    public void assignToPlayer(Player player){
        this.player = player;
    }

}
