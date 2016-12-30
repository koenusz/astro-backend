package astro.backend.server.event.entities.combat;

import astro.backend.server.event.frame.Event;
import lombok.Getter;

@Getter
public class AttackEvent implements Event {

    private final long attackerId;

    private final long targetId;

    public AttackEvent(long attackerId, long targetId) {
        this.attackerId = attackerId;
        this.targetId = targetId;
    }
}
