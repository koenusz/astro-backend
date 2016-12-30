package astro.backend.server.model.components;

import astro.backend.server.engine.AbstractComponent;
import lombok.Getter;

@Getter
public class Hull extends AbstractComponent {


    private final long entityId;

    private final int hullSize;


    public Hull(long id, long entityId, long entityId1, int hullSize) {
        super(id, entityId);
        this.entityId = entityId1;
        this.hullSize = hullSize;
    }
}
