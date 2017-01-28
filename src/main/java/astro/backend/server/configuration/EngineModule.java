package astro.backend.server.configuration;

import artemis.Simulator;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.EngineDataStore;
import astro.backend.server.engine.IdProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

public class EngineModule extends AbstractModule {


    private Engine engine;
    private Simulator simulator;


    @Inject
    public EngineModule() {


    }

    @Provides
    public Engine provideEngine(IdProvider idProvider, EngineDataStore engineDataStore){
       if(engine == null && idProvider == null){
           engine = new Engine();
       } else if (engine == null){
           engine = new Engine(idProvider);
       }
        return engine;
    }

    @Provides
    public Simulator provideSimulator(){
        return simulator;
    }


    @Override
    protected void configure() {
        simulator = new Simulator();
      //  bind(CombatSystem.class).toProvider(new SystemProvider<>(CombatSystem.class, simulator, engine));
    }
}
