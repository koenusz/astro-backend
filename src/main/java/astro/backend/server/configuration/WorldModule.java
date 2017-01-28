package astro.backend.server.configuration;

import artemis.EntitySubscriberSystem;
import artemis.TileSpecialisationSystem;
import artemis.component.Surface;
import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class WorldModule extends AbstractModule {


    private World world;

    @Override
    protected void configure() {

        WorldConfiguration config = new WorldConfigurationBuilder()
                // .dependsOn(MyPlugin.class)
                .with(
                        new TileSpecialisationSystem(Aspect.all(Surface.class)),
                        new EntitySubscriberSystem(Aspect.all())
                ).build();

        world = new World(config);
    }

    @Provides
    public World provideWorld(){
        return world;
    }
}
