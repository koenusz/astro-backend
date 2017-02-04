package astro.backend.server.event.entities.combat;

import astro.backend.server.event.frame.Event;
import netty.Player;

public class ModifiedAttackMessage implements Event {
    @Override
    public void assignToPlayer(Player player) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }

    // private final Weapon weapon;

    //public ModifiedAttackMessage(Weapon weapon) {
//        this.weapon = weapon;
//    }
}
