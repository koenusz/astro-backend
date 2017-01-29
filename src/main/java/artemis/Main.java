package artemis;

import artemis.component.Surface;
import artemis.component.Terrain;
import com.artemis.*;
import javaslang.collection.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();


    public static void main (String... args){

        logger.info("starting");

        WorldConfiguration config = new WorldConfigurationBuilder()
                // .dependsOn(MyPlugin.class)
                .with(
                        new TileSpecialisationSystem(Aspect.all(Surface.class)),
                        new EntitySubscriberSystem(Aspect.all())
                        ).build();

        World world = new World(config);
        int planet = world.create();

        ComponentMapper<Surface> surfaceComponentMapper = world.getMapper(Surface.class);
        Surface s = surfaceComponentMapper.create(planet);
        s.sizeX = 2;
        s.sizeY = 2;
        Terrain terrain1 = new Terrain();
        terrain1.specialisation = Terrain.Specialisation.None;
        terrain1.terrainType = Terrain.TerrainType.Arctic;
        terrain1.positionX = 0;
        terrain1.positionY = 0;

        Terrain terrain2 = new Terrain();
        terrain2.specialisation = Terrain.Specialisation.None;
        terrain2.terrainType = Terrain.TerrainType.Arctic;
        terrain2.positionX = 0;
        terrain2.positionY = 0;

        Terrain terrain3 = new Terrain();
        terrain3.specialisation = Terrain.Specialisation.None;
        terrain3.terrainType = Terrain.TerrainType.Arctic;
        terrain3.positionX = 0;
        terrain3.positionY = 0;

        Terrain terrain4 = new Terrain();
        terrain4.specialisation = Terrain.Specialisation.None;
        terrain4.terrainType = Terrain.TerrainType.Arctic;
        terrain4.positionX = 0;
        terrain4.positionY = 0;

        s.tiles = List.of(terrain1, terrain2, terrain3, terrain4);

        logger.info("entity {} ", world.getEntity(planet));
        logger.debug("test");


        world.process();
        EntitySubscriberSystem entitySubscriberSystem = world.getSystem(EntitySubscriberSystem.class);



    }
}
