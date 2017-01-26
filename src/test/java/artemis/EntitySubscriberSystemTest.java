package artemis;

import artemis.component.Player;
import artemis.component.Surface;
import artemis.component.Tile;
import com.artemis.*;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.control.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntitySubscriberSystemTest {

    private static final Logger logger = LogManager.getLogger();


    private World world;
    private EntitySubscriberSystem entitySubscriberSystem;
    private Player player;



    @Test
    public void subscribeTest(){
        entitySubscriberSystem.subscribe(player, HashSet.of(0));
        world.process();
        assertTrue(entitySubscriberSystem.isSubscribed(player));
    }

    @Test
    public void requestPlanet() {

        entitySubscriberSystem.subscribe(player, HashSet.of(0));
        world.process();
        //subscriptions take 1 update to get processed since subscriptions process at the end of the cycle.
        world.process();
        Option<Set<Integer>> result =  entitySubscriberSystem.getPlayerUpdate(player);
        assertTrue(result.isDefined());
        assertTrue(result.get().contains(0));

    }

    @Test
    public void unsubscribeTest(){

        entitySubscriberSystem.subscribe(player, HashSet.of(0));
        world.process();
        //subscriptions take 1 update to get processed since subscriptions process at the end of the cycle.
        world.process();
        Option<Set<Integer>> resultSub =  entitySubscriberSystem.getPlayerUpdate(player);
        assertTrue(resultSub.isDefined());
        assertTrue(resultSub.get().contains(0));

        entitySubscriberSystem.unsubscribe(player);
        world.process();
        //subscriptions take 1 update to get processed since subscriptions process at the end of the cycle.
        world.process();
        Option<Set<Integer>> resultUnsub =  entitySubscriberSystem.getPlayerUpdate(player);
        assertFalse(entitySubscriberSystem.isSubscribed(player));
        assertFalse(resultUnsub.isDefined());
    }

    @BeforeEach
    public void init() {

        WorldConfiguration config = new WorldConfigurationBuilder()
                // .dependsOn(MyPlugin.class)
                .with(
                        new EntitySubscriberSystem(Aspect.all())
                ).build();

        world = new World(config);
        player = new Player();
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

        entitySubscriberSystem = world.getSystem(EntitySubscriberSystem.class);
    }


}
