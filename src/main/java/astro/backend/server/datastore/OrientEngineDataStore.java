package astro.backend.server.datastore;

import astro.backend.server.engine.Component;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.EngineDataStore;
import astro.backend.server.engine.Entity;

import java.util.HashMap;
import java.util.Map;

public class OrientEngineDataStore implements EngineDataStore {


    private Map<Class<?>, ComponentStore> doaMap = new HashMap<>();

    public void registerDoa(ComponentStore componentStore){
        doaMap.put(componentStore.getType(), componentStore);
    }

    @Override
    public Entity saveEntity(Entity entity) {


        return null;
    }

    @Override
    public Entity readEntity(long entityId) {
        return null;
    }

    @Override
    public void deleteEntity(long entityId) {
            }

    @Override
    public Component saveComponent(Component component) {
        //ComponentStore doa = doaMap.get(Engine.getComponentType(component));
        return null;//doa.save(component);
    }

    @Override
    public Component readComponent(long componentId) {
        return null;
    }

    @Override
    public void deleteComponent(long componentId) {

    }



}
