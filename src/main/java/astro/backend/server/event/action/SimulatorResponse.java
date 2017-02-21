package astro.backend.server.event.action;

import lombok.Getter;

@Getter
public class SimulatorResponse {

    private final boolean play;
    private final boolean pending;

    public SimulatorResponse(boolean play, boolean pending) {
        this.play = play;
        this.pending = pending;
    }

}
