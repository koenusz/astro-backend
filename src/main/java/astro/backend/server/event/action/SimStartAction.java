package astro.backend.server.event.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimStartAction extends ActionEvent {

    @JsonCreator
    public SimStartAction(@JsonProperty("type") String type) {
        super(type, null);
    }
}
