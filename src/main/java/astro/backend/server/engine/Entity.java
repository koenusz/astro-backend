package astro.backend.server.engine;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class Entity {

    private final long id;

    private final List<Component> components;

    public Entity(long id, List<Component> components) {
        this.id = id;
        this.components = components;
    }

    public <C extends Component> C getComponent(Class<C> componentType){
        return componentType.cast(components.stream()
                .filter(component -> component.getClass().equals(componentType))
                .findFirst().orElse(null));
    }
}
