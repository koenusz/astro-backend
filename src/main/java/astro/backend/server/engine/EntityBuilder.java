package astro.backend.server.engine;

import java.util.List;

public interface EntityBuilder<E extends Entity> {

    Class<E> getType();

    E create();

    EntityBuilder<E> setId(long id);

    EntityBuilder setComponentBuilders(ComponentBuilder... componentBuilders);

    List<ComponentBuilder> getComponentBuilders();

    EntityBuilder setComponents(Component... components);

}
