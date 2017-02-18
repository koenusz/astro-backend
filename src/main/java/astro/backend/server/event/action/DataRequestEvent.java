package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javaslang.collection.List;
import lombok.Getter;
import netty.Player;


@Getter
public class DataRequestEvent implements Event {

    private String entityType;
    private List<String> components;
    private  List<Integer> entityIds;
    private Player player;

    @JsonCreator
    public DataRequestEvent(@JsonProperty("entityType") String entityType
            , @JsonProperty("components") List<String> components
            , @JsonProperty("entityId") List<Integer> entityIds) {
        this.entityType = entityType;
        this.components = components;
        this.entityIds = entityIds;
    }

    public void assignToPlayer(Player player){
        this.player = player;
    }

}
