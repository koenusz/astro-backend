package astro.backend.server.configuration;

import astro.backend.server.engine.Engine;
import astro.backend.server.engine.GameObject;
import astro.backend.server.engine.Simulator;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

class SystemProvider<G extends GameObject> implements Provider<G>{


    @Inject
    Injector injector;

    private final Class<G> type;
    private Simulator simulator;
    private Engine engine;

    SystemProvider(Class<G> type, Simulator simulator, Engine engine) {
        this.type = type;
        this.simulator = simulator;
        this.engine = engine;
    }

    @Override
    public G get() {
        try {
            GameObject go = type.newInstance();
            injector.injectMembers(go);
            simulator.subscribe(go);
            return type.cast(go);
        }  catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error instantiating system of type: " + type.getSimpleName(), e);
        }
    }
}
