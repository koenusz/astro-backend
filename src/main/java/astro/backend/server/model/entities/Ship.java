package astro.backend.server.model.entities;

import astro.backend.server.engine.Component;
import astro.backend.server.engine.ComponentBuilder;
import astro.backend.server.engine.Entity;
import astro.backend.server.engine.EntityBuilder;

import java.util.Arrays;
import java.util.List;


public class Ship extends Entity{

    private Ship(long id, List<Component> components) {
        super(id, components);
    }

    public static class ShipBuilder implements EntityBuilder<Ship> {
        private long id;
        private List<Component> components;
        private List<ComponentBuilder> componentBuilders;

        @Override
        public Class<Ship> getType() {
            return Ship.class;
        }


        public ShipBuilder setId(long id) {
            this.id = id;
            return this;
        }

        @Override
        public EntityBuilder setComponentBuilders(ComponentBuilder... componentBuilders) {
            this.componentBuilders = Arrays.asList(componentBuilders);
            return this;
        }

        @Override
        public List<ComponentBuilder> getComponentBuilders() {
            return componentBuilders;
        }

        public ShipBuilder setComponents(Component... components) {
            this.components = Arrays.asList(components);
            return this;
        }

        @Override
        public Ship create() {
            return new Ship(id, components);
        }
    }
}
