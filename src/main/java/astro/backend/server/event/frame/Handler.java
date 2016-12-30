package astro.backend.server.event.frame;

@FunctionalInterface
public interface Handler<E extends Event> {

    void onEvent(E event);
    
}
