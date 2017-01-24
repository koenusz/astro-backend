package astro.backend.server.engine;

import astro.backend.server.datastore.AbstractDoa;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.inject.Inject;

import java.util.Optional;

public class ComponentProvider<C extends Component> {

    private Engine engine;

    private AbstractDoa abstractDoa;

    private TypeResolver typeResolver = new TypeResolver();

    @Inject
    public ComponentProvider(Engine engine, AbstractDoa abstractDoa) {
        this.engine = engine;
        this.abstractDoa = abstractDoa;
    }

    @SuppressWarnings("unchecked")
    public Optional<C> get(long entityId){
        ResolvedType type = typeResolver.resolve(this.getClass());
        Class erased = type.getErasedType();
        return engine.findComponent(erased, entityId);
    }
}
