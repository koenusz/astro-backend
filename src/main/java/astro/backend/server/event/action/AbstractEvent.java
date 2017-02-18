package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import lombok.Getter;
import netty.Player;

@Getter
public abstract class AbstractEvent implements Event {

    private Player player;

    public void assignToPlayer(Player player) {
        this.player = player;
    }

}
