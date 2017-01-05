package astro.backend.server.model;

import astro.backend.server.configuration.EngineModule;
import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.engine.Component;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Entity;
import astro.backend.server.engine.Simulator;
import astro.backend.server.model.entities.Planet;
import astro.backend.server.model.planet.components.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanetComponentTest {


    private Engine engine;

    private Injector injector;

    private Surface.Builder surfaceBuilder;

    @BeforeEach
    private void init() {

        injector = Guice.createInjector(new OrientDBModule("memory:crudtest", "admin", "admin"), new EngineModule());
        engine = injector.getInstance(Engine.class);

        Tile tile = ImmutableTile.builder()
                .terrainType(Tile.TerrainType.Forest)
                .position(ImmutablePosition.builder().x(0).y(0).build())
                .build();


        surfaceBuilder = ImmutableSurface.builder().addTiles(tile)
                .size(ImmutableSize.builder().x(1).y(1).build());

    }


    private Planet createPlanet() {
        List<Component.ComponentBuilder> builders = new ArrayList<>();
        builders.add(surfaceBuilder);
        return engine.createEntity(Planet.class, builders);
    }

    @Test
    void entityConstructionTest() {

        Planet planet = this.createPlanet();
        Assertions.assertTrue(planet.getId() > 0);
    }

    @Test
    void componentFindTest() {
        Planet planet = this.createPlanet();
        List<Component> found = engine.findComponentsByEntityId(planet.getId());
        Assertions.assertTrue(planet.getComponents().containsAll(found));
        Surface surface = planet.getComponent(Surface.class);
        Optional<Surface> foundSurface = engine.findComponent(Surface.class, surface.getComponentId());
        Assertions.assertEquals(surface, foundSurface.get());
    }

    @Test
    void EntityFindTest() {
        Planet planet = this.createPlanet();
        Optional<Planet> found = engine.findEntity(Planet.class, planet.getId());
        Assertions.assertEquals(planet, found.get());
        Optional<Entity> found2 = engine.findEntity(planet.getId());
        Assertions.assertEquals(planet, found2.get());
    }

    @Test
    void engineModuleTest() {
        Simulator simulator = injector.getInstance(Simulator.class);
        Assertions.assertNotNull(simulator);
        Simulator simulator2 = injector.getInstance(Simulator.class);
        Assertions.assertEquals(simulator, simulator2);

        Engine engine2 = injector.getInstance(Engine.class);
        Assertions.assertEquals(engine, engine2);
    }

    @Test
    @Disabled
    void atttackTest() {

        ////CombatSystem cs = injector.getInstance(CombatSystem.class);
       // Assertions.assertNotNull(cs);

//        Planet planet1 = this.createPlanet("red");
//        Planet planet2 = this.createPlanet("blue");

//        //move this to guice
//        Queue<Event> eventQueue = new LinkedList<>();
//        eventQueue.add(new AttackEvent(planet1.getId(), planet2.getId()));
//
//        EventDispatcher dispatcher = new EventDispatcher();
//        //dispatcher.registerHandler(AttackEvent.class, new AttackHandler(cs));
//
//        while (!eventQueue.isEmpty()) {
//            Event evt = eventQueue.remove();
//            dispatcher.dispatch(evt);
//        }


    }


}
