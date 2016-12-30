package astro.backend.server.service;

@FunctionalInterface
public interface Order<T> {


    void order(T ordered);

}
