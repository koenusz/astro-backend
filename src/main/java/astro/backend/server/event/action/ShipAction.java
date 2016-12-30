package astro.backend.server.event.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;


public class ShipAction extends ActionEvent {


    @JsonCreator
    public ShipAction(@JsonProperty String type, @JsonProperty("ship") JsonNode ship){
       super(type, ship);
    }
}
