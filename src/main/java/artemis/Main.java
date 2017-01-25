package artemis;

import artemis.component.Surface;
import artemis.component.Tile;
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
        Tile tile1 = new Tile();
        tile1.specialisation = Tile.Specialisation.None;
        tile1.terrainType = Tile.TerrainType.Arctic;
        tile1.positionX = 0;
        tile1.positionY = 0;

        Tile tile2 = new Tile();
        tile2.specialisation = Tile.Specialisation.None;
        tile2.terrainType = Tile.TerrainType.Arctic;
        tile2.positionX = 0;
        tile2.positionY = 0;

        Tile tile3 = new Tile();
        tile3.specialisation = Tile.Specialisation.None;
        tile3.terrainType = Tile.TerrainType.Arctic;
        tile3.positionX = 0;
        tile3.positionY = 0;

        Tile tile4 = new Tile();
        tile4.specialisation = Tile.Specialisation.None;
        tile4.terrainType = Tile.TerrainType.Arctic;
        tile4.positionX = 0;
        tile4.positionY = 0;

        s.tiles = List.of(tile1, tile2, tile3, tile4);

        logger.info("entity {} ", world.getEntity(planet));
        logger.debug("test");


        world.process();
        EntitySubscriberSystem entitySubscriberSystem = world.getSystem(EntitySubscriberSystem.class);



    }
}
