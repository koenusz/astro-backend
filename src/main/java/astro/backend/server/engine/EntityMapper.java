package astro.backend.server.engine;

import java.util.List;

public class EntityMapper<E extends Entity> {


    private Class<E> type;

    private Engine engine;

    public EntityMapper(Engine engine, Class<E> type) {
        this.engine = engine;
        this.type = type;
    }

    public E create(List<Component.ComponentBuilder> builders){
        return engine.createEntity(type, builders);
    }


}
