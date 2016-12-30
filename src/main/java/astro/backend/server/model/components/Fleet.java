package astro.backend.server.model.components;

import astro.backend.server.engine.AbstractComponent;
import astro.backend.server.engine.ComponentBuilder;

public class Fleet extends AbstractComponent {


    private final String lastOrder;

    private final String position;

    private final String faction;


    private Fleet(long id, long entityId, String lastOrder, String position, String faction) {
        super(id, entityId);
        this.lastOrder = lastOrder;
        this.position = position;
        this.faction = faction;
    }

    public static class FleetBuilder implements ComponentBuilder {
        private long id;
        private long entityId;
        private String lastOrder;
        private String position;
        private String faction;

        public FleetBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public FleetBuilder setEntityId(long entityId) {
            this.entityId = entityId;
            return this;
        }

        public FleetBuilder setLastOrder(String lastOrder) {
            this.lastOrder = lastOrder;
            return this;
        }

        public FleetBuilder setPosition(String position) {
            this.position = position;
            return this;
        }

        public FleetBuilder setFaction(String faction) {
            this.faction = faction;
            return this;
        }

        @Override
        public Class getType() {
            return Fleet.class;
        }

        public Fleet create() {
            return new Fleet(id, entityId, lastOrder, position, faction);
        }
}}
