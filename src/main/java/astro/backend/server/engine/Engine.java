package astro.backend.server.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Engine {

    private static final Logger logger = LogManager.getLogger();
    private Map<Class<? extends Component>, List<Component>> components = new HashMap<>();
    private long lastComponentId = 0;
    private Map<Class<? extends Entity>, List<Entity>> entities = new HashMap<>();
    private long lastEntityId = 0;


    private long getComponentId() {
        return ++lastComponentId;
    }

    private long getEntityId() {
        return ++lastEntityId;
    }

    public <C extends Component> C updateComponent(ComponentBuilder<C> builder) {
        C c = builder.create();
        //we could consider changing this to an array and using the array index as component id if this causes performance issues.
        List<Component> componentList = components.getOrDefault(builder.getType(), new ArrayList<>());
        componentList.removeIf(component -> component.getId() == c.getId());
        componentList.add(c);
        components.put(builder.getType(), componentList);
        return c;
    }

    public <C extends Component> C createComponent(ComponentBuilder<C> builder) {
        builder.setId(getComponentId());
        C c = builder.create();
        List<Component> componentList = components.getOrDefault(builder.getType(), new ArrayList<>());
        componentList.add(c);
        components.put(builder.getType(), componentList);
        return c;
    }

    public <E extends Entity> E createEntity(EntityBuilder<E> builder) {
        long entityId = getEntityId();
        builder.setId(entityId);

        Component[] components = builder.getComponentBuilders().stream()
                .map(componentBuilder -> componentBuilder.setEntityId(entityId))
                .map(this::createComponent)
                .toArray(Component[]::new);
        builder.setComponents(components);
        E e = builder.create();
        List<Entity> entityList = entities.getOrDefault(builder.getType(), new ArrayList<>());
        entityList.add(e);
        entities.put(builder.getType(), entityList);
        return e;

    }

    public <C extends Component> boolean removeComponent(Class<C> componentType, long id) {
        List<Component> componentList = components.getOrDefault(componentType, new ArrayList<>());
        return componentList.removeIf(c -> c.getId() == id);
    }

    public <C extends Component> C findComponent(Class<C> componentType, long id) {
        List<Component> componentList = components.getOrDefault(componentType, new ArrayList<>());
        return componentType.cast(componentList.stream().filter(c -> c.getId() == id).findFirst().orElse(null));
    }

    public List<Component> findComponentsByEntityId(long entityId) {

        return components.entrySet().parallelStream()
                .flatMap(list -> list.getValue().stream())
                .filter(component -> component.getEntityId() == entityId)
                .collect(Collectors.toList());
    }

    public <E extends Entity> E findEntity(Class<E> entityType, long id) {
        List<Entity> entityList = entities.getOrDefault(entityType, new ArrayList<>());
        return entityType.cast(entityList.stream().filter(e -> e.getId() == id).findFirst().orElse(null));
    }

    public Entity findEntity(long id) {
        return entities.entrySet().stream()
                .flatMap(list -> list.getValue().stream())
                .filter(entity -> entity.getId() == id).findFirst().orElse(null);

    }

}
