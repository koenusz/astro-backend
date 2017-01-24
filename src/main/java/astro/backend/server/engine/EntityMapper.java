package astro.backend.server.engine;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.inject.Inject;

import java.util.List;

public class EntityProvider<E extends Entity> {


    private Class<E> type;

    private Engine engine;

    @Inject
    private EntityProvider(Engine engine) {
        this.engine = engine;
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType type = typeResolver.resolve(this.getClass());
        this.type = (Class<E>) type.getErasedType();
    }

    public E create(List<Component.ComponentBuilder> builders){
        return engine.createEntity(type, builders);
    }


}
