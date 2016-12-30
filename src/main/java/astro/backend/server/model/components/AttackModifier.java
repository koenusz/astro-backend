package astro.backend.server.model.components;

import astro.backend.server.engine.AbstractComponent;
import lombok.Getter;

@Getter
public class AttackModifier extends AbstractComponent {

    private final long entityId;


    private final int armor;

    public AttackModifier(long id, long entityId, long entityId1, int armor) {
        super(id, entityId);
        this.entityId = entityId1;
        this.armor = armor;
    }
}
