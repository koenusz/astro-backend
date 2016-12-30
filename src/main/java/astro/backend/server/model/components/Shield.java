package astro.backend.server.model.components;

import astro.backend.server.engine.AbstractComponent;
import astro.backend.server.engine.ComponentBuilder;
import lombok.Getter;

@Getter
public class Shield extends AbstractComponent {

    private final int strength;

    private Shield(long id, long entityId, int strength) {
        super(id, entityId);
        this.strength = strength;
    }

    public static class ShieldBuilder implements ComponentBuilder<Shield> {
        private long id;
        private long entityId;
        private int strength;

        public ShieldBuilder setShield(Shield shield){
            this.id = shield.getId();
            this.entityId = shield.getEntityId();
            this.strength = shield.getStrength();
            return this;
        }

        public ShieldBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ShieldBuilder setEntityId(long entityId) {
            this.entityId = entityId;
            return this;
        }

        public ShieldBuilder setStrength(int strength) {
            this.strength = strength;
            return this;
        }

        @Override
        public Class<Shield> getType() {
            return Shield.class;
        }

        public Shield create() {
            return new Shield(id, entityId, strength);
        }


    }
}