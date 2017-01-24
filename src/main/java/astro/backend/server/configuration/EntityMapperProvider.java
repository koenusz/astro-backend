package astro.backend.server.configuration;

import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Entity;
import astro.backend.server.engine.EntityMapper;
import com.google.inject.Provider;

public class EntityMapperProvider<E extends Entity> implements Provider<EntityMapper<E>>{


    private Class<E> mapperType;

    private Engine engine;

    public EntityMapperProvider(Class<E> mapperType, Engine engine) {
        this.mapperType = mapperType;
        this.engine = engine;
    }

    @Override
    public EntityMapper<E> get() {
        return new EntityMapper(engine, mapperType);
    }
}
