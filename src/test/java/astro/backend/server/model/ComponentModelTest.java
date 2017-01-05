package astro.backend.server.model;

import astro.backend.server.configuration.EngineModule;
import astro.backend.server.engine.Component;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Entity;
import astro.backend.server.engine.Simulator;
import astro.backend.server.event.entities.combat.AttackEvent;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;
import astro.backend.server.model.entities.Ship;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class ComponentModelTest {


    private Engine engine;

    private Injector injector;

    @BeforeEach
    private void initMethodContext() {

        injector = Guice.createInjector(new EngineModule());
        engine = injector.getInstance(Engine.class);
    }

    private Ship createShip() {
        return createShip("red");
    }


    private Ship createShip(String faction) {
      //  Ship.ShipBuilder builder = new Ship.ShipBuilder();
//        Weapon.WeaponBuilder weaponBuilder = new Weapon.WeaponBuilder().setDamage(1);
//        Shield.ShieldBuilder shieldBuilder = new Shield.ShieldBuilder().setStrength(2);
//        Fleet.FleetBuilder fleetBuilder = new Fleet.FleetBuilder().setFaction(faction);
//        builder.setComponentBuilders(weaponBuilder, shieldBuilder, fleetBuilder);
        return null; //engine.createEntity(builder);
    }

    @Test
    void entityConstructionTest() {

        Ship ship = this.createShip();
        Assertions.assertTrue(ship.getId() > 0);
    }

    @Test
    void componentFindTest() {
        Ship ship = this.createShip();
        List<Component> found = engine.findComponentsByEntityId(ship.getId());
        Assertions.assertTrue(ship.getComponents().containsAll(found));
//        Weapon w = ship.getComponent(Weapon.class);
//        Weapon foundWeapon = engine.findComponent(Weapon.class, w.getComponentId());
//        Assertions.assertEquals(w, foundWeapon);
    }

    @Test
    void EntityFindTest() {
        Ship ship = this.createShip();
        Optional<Ship> found = engine.findEntity(Ship.class, ship.getId());
        Assertions.assertEquals(ship, found.get());
        Optional<Entity> found2 = engine.findEntity(ship.getId());
        Assertions.assertEquals(ship, found2.get());
    }

    @Test
    void engineModuleTest() {
      //  CombatSystem cs = injector.getInstance(CombatSystem.class);
       // Assertions.assertNotNull(cs);

        Simulator simulator = injector.getInstance(Simulator.class);
        Assertions.assertNotNull(simulator);
        Simulator simulator2 = injector.getInstance(Simulator.class);
        Assertions.assertEquals(simulator, simulator2);

        Engine engine2 = injector.getInstance(Engine.class);
        Assertions.assertEquals(engine, engine2);
    }

    @Test
    void atttackTest() {

       // CombatSystem cs = injector.getInstance(CombatSystem.class);
        //Assertions.assertNotNull(cs);

        Ship ship1 = this.createShip("red");
        Ship ship2 = this.createShip("blue");

        //move this to guice
        Queue<Event> eventQueue = new LinkedList<>();
        eventQueue.add(new AttackEvent(ship1.getId(), ship2.getId()));

        EventDispatcher dispatcher = new EventDispatcher();
      //  dispatcher.registerHandler(AttackEvent.class, new AttackHandler(cs));

        while (!eventQueue.isEmpty()) {
            Event evt = eventQueue.remove();
            dispatcher.dispatch(evt);
        }



    }
}
