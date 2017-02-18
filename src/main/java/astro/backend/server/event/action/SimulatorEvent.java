package astro.backend.server.event.action;

import lombok.Getter;

@Getter
public class SimulatorEvent extends AbstractEvent {

    private boolean start;

    public SimulatorEvent(boolean start) {
        this.start = start;
        //        if (play.equals("play")) {
//            this.start = true;
//        } else {
//            this.start = false;
//        }
    }
}
