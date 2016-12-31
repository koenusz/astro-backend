package astro.backend.server.engine;

public interface ComponentStore {

    Component saveComponent(Component component);

    Component readComponent(Component component);

    Component deleteComponent(Component component);

}
