package astro.backend.server.event.entities.combat;

import astro.backend.server.event.frame.Event;
import astro.backend.server.model.components.Weapon;

public class ModifiedAttackMessage implements Event {

    private final Weapon weapon;

    public ModifiedAttackMessage(Weapon weapon) {
        this.weapon = weapon;
    }
}
