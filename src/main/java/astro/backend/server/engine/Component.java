package astro.backend.server.engine;

import astro.backend.server.datastore.Storable;

public interface Component extends Storable {

    long getComponentId();

    long getEntityId();

    interface ComponentBuilder extends StorableBuilder{

        Component build();

        ComponentBuilder from(Storable storable);

        ComponentBuilder componentId(long id);

        ComponentBuilder entityId(long id);

    }

}
