package astro.backend.server.event.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SimulatorEvent extends AbstractEvent {

    private boolean start;
    private int speed;


    @JsonCreator
    public SimulatorEvent(@JsonProperty("start")boolean start, @JsonProperty("speed") int speed) {
        this.start = start;
        this.speed = speed;
    }
}
