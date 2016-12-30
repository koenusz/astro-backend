package astro.backend.server.engine;

public interface ComponentBuilder<C extends Component> {

    Class<C> getType();

    C create();

    ComponentBuilder<C> setId(long id);

    ComponentBuilder<C> setEntityId(long id);

}
