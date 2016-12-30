package astro.backend.server.configuration;

import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Simulator;
import astro.backend.server.model.systems.CombatSystem;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class EngineModule extends AbstractModule {


    private final Engine engine;

    private final Simulator simulator;

    public EngineModule() {
        engine = new Engine();
        simulator = new Simulator();

    }

    @Provides
    public Engine provideEngine(){
        return engine;
    }

    @Provides
    public Simulator provideSimulator(){
        return simulator;
    }


    @Override
    protected void configure() {
        bind(CombatSystem.class).toProvider(new SystemProvider<>(CombatSystem.class, simulator, engine));
    }
}
