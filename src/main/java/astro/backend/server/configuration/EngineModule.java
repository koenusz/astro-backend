package astro.backend.server.configuration;

import astro.backend.server.engine.*;
import astro.backend.server.model.entities.Planet;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

public class EngineModule extends AbstractModule {


    private Engine engine;
    private final Simulator simulator;


    @Inject
    public EngineModule() {
        simulator = new Simulator();

    }

    @Provides
    public Engine provideEngine(IdProvider idProvider){
        engine.configureIdProvider(idProvider);
        return engine;
    }

    @Provides
    public Simulator provideSimulator(){
        return simulator;
    }


    @Override
    protected void configure() {
        engine = new Engine();
      bind(new TypeLiteral<EntityMapper<Planet>>() {}).toProvider(new EntityMapperProvider(Planet.class, engine));
      //  bind(CombatSystem.class).toProvider(new SystemProvider<>(CombatSystem.class, simulator, engine));
    }
}
