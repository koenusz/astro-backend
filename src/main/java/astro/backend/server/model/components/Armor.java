package astro.backend.server.model.components;

import astro.backend.server.engine.AbstractComponent;
import lombok.Getter;

@Getter
public class Armor extends AbstractComponent {

    private final int armor;


    public Armor(long id, long entityId, int armor) {
        super(id, entityId);
        this.armor = armor;
    }
}
