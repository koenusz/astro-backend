package astro.backend.server.event.action;

import lombok.Getter;

@Getter
public class SimulatorTickResponse {

    private final double time;

    public SimulatorTickResponse(double time) {
        this.time = time;
    }
}
