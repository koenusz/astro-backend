package astro.backend.server.event.frame;

public class NoHandlerRegisteredException extends RuntimeException {


    public NoHandlerRegisteredException(String message) {
        super(message);
    }
}
