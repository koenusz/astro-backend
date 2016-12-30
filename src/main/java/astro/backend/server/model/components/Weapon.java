package astro.backend.server.model.components;

import astro.backend.server.engine.AbstractComponent;
import astro.backend.server.engine.ComponentBuilder;
import lombok.Getter;

@Getter
public class Weapon extends AbstractComponent {


    private final int damage;

    public Weapon(long id, long entityId, int damage) {
        super(id, entityId);
        this.damage = damage;
    }

    public static class WeaponBuilder implements ComponentBuilder<Weapon> {
        private long id;
        private long entityId;
        private int damage;

        public WeaponBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public WeaponBuilder setEntityId(long entityId) {
            this.entityId = entityId;
            return this;
        }

        public WeaponBuilder setDamage(int damage) {
            this.damage = damage;
            return this;
        }

        @Override
        public Class<Weapon> getType() {
            return Weapon.class;
        }

        public Weapon create() {
            return new Weapon(id, entityId, damage);
        }
    }
}
