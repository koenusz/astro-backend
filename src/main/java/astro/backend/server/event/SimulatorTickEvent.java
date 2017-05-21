package astro.backend.server.event;

import astro.backend.server.event.frame.Event;
import lombok.Getter;
import netty.Player;

@Getter
public class SimulatorTickEvent implements Event {

    private final double time;

    public SimulatorTickEvent(double time) {
        this.time = time;
    }

    @Override
    public void assignToPlayer(Player player) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }
}
