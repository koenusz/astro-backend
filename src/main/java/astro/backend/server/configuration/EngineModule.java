package astro.backend.server.configuration;

import astro.backend.server.ActionQueue;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.IdProvider;
import astro.backend.server.engine.Simulator;
import astro.backend.server.event.frame.EventDispatcher;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import java.util.concurrent.ExecutorService;

public class EngineModule extends AbstractModule {


    private Engine engine;
    private final Simulator simulator;


    @Inject
    public EngineModule() {
        simulator = new Simulator();

    }

    @Provides
    public Engine provideEngine(IdProvider idProvider){
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
      //  bind(CombatSystem.class).toProvider(new SystemProvider<>(CombatSystem.class, simulator, engine));
    }
}
