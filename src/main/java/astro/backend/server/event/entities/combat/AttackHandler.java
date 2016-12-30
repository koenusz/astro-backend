package astro.backend.server.event.entities.combat;

import astro.backend.server.event.frame.Handler;
import astro.backend.server.model.systems.CombatSystem;

public class AttackHandler implements Handler<AttackEvent> {

    private final CombatSystem combatSystem;

    public AttackHandler(CombatSystem combatSystem) {
        this.combatSystem = combatSystem;
    }

    @Override
    public void onEvent(AttackEvent event) {
        combatSystem.resolveAttack(event.getAttackerId(), event.getTargetId());
    }
}
