package artemis;


import artemis.component.Surface;
import artemis.component.Terrain;
import com.artemis.*;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.control.Option;
import netty.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        player = new Player(null);
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

        entitySubscriberSystem = world.getSystem(EntitySubscriberSystem.class);
    }


}
