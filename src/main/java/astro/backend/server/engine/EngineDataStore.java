package astro.backend.server.engine;

import astro.backend.server.datastore.ComponentStore;

public interface EngineDataStore {

    void registerDoa(ComponentStore store);

    Entity saveEntity(Entity entity);

    Entity readEntity(long entityId);

    void deleteEntity(long entityId);

    Component saveComponent(Component entity);

    Component readComponent(long componentId);

    void deleteComponent(long componentId);
}
