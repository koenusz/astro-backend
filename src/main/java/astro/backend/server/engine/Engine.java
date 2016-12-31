package astro.backend.server.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static astro.backend.server.engine.Component.ComponentBuilder;


public class Engine {

    private static final Logger logger = LogManager.getLogger();
    private Map<Class<? extends Component>, List<Component>> components = new HashMap<>();
    private long lastComponentId = 0;
    private Map<Class<? extends Entity>, List<Entity>> entities = new HashMap<>();
    private long lastEntityId = 0;

    private IdProvider idProvider;

    public Engine() {
    }

    public Engine(IdProvider idProvider) {
        this.idProvider = idProvider;
    }

    private long getComponentId() {
        if (idProvider != null) {
            return idProvider.getComponentId();
        }
        return ++lastComponentId;
    }

    private long getEntityId() {
        if (idProvider != null) {
            return idProvider.getEntityId();
        }
        return ++lastEntityId;
    }

    //Todo recieved pushed updates from the datastore
    public void recieveComponentUpdate() {

    }

    public Component updateComponent(ComponentBuilder builder) {
        Component c = builder.build();
        //we could consider changing this to an array and using the array index as component id if this causes performance issues.
        Class[] interfaces = c.getClass().getInterfaces();
        if(interfaces.length != 1) {
            throw new RuntimeException("cannot determine component type");
        }

        List<Component> componentList = components.getOrDefault(interfaces[0], new ArrayList<>());
        componentList.removeIf(component -> component.getComponentId() == c.getComponentId());
        componentList.add(c);
        components.put(interfaces[0], componentList);
        return c;
    }

    public Component createComponent(Component.ComponentBuilder builder) {
        builder.componentId(getComponentId());
        Component c = builder.build();
        List<Component> componentList = components.getOrDefault(c.getClass(), new ArrayList<>());
        componentList.add(c);

        Class[] interfaces = c.getClass().getInterfaces();
        if(interfaces.length != 1) {
        throw new RuntimeException("cannot determine component type");
        }

        components.put(interfaces[0], componentList);
        return c;
    }

    public <E extends Entity> E createEntity(Class<E> type, List<Component.ComponentBuilder> builders) {
        long entityId = getEntityId();
        List<Component> components = builders
                .stream().map(builder -> {
                    builder.entityId(entityId);
                    return this.createComponent(builder);
                }
        ).collect(Collectors.toList());
        try {
            E entity = type.getDeclaredConstructor(Long.TYPE, List.class).newInstance(entityId, components);
            List<Entity> entityList = entities.getOrDefault(type, new ArrayList<>());
                    entityList.add(entity);
            entities.put(type, entityList);
            return entity;


        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("failed to create entity of type " + type, e);
        }
    }

    public <C extends Component> boolean removeComponent(Class<C> componentType, long id) {
        List<Component> componentList = components.getOrDefault(componentType, new ArrayList<>());
        return componentList.removeIf(c -> c.getComponentId() == id);
    }

    public <C extends Component> C findComponent(Class<C> componentType, long id) {
        List<Component> componentList = components.getOrDefault(componentType, new ArrayList<>());
        return componentType.cast(componentList.stream().filter(c -> c.getComponentId() == id).findFirst().orElse(null));
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
