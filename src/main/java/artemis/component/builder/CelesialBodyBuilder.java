package artemis.component.builder;

import artemis.component.Position;
import artemis.component.Subscription;
import artemis.component.Surface;
import artemis.component.Terrain;
import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.google.inject.Inject;

public class CelesialBodyBuilder {

    // 1x1 3x4 4x5 5x6 6x7 7x8
    public enum Size {Asteroid, Tiny, Small, Medium, Large, Huge}

    private World world;

    private Archetype star;
    private Archetype planet;
    private Archetype asteroid;


    @Inject
    private CelesialBodyBuilder(World world){
        this.world = world;
        planet = planet();
        star = star();
        asteroid = asteroid();

    }

    private ArchetypeBuilder celestialBobyArchetype(){
        return new ArchetypeBuilder()
                .add(Position.class)
                .add(Subscription.class);

    }

    private Archetype star(){
        return celestialBobyArchetype()
                .build(world);
    }

    public int buildStar(){
        int star = world.create(this.star);
        return star;

    }

    private Archetype planet(){
        return celestialBobyArchetype()
                .add(Surface.class)
                .build(world);
    }

    public int buildPlanet(Size size, Terrain.TerrainType type){
        int planet = world.create(this.planet);
        return planet;
    }

    private Archetype asteroid(){
        return celestialBobyArchetype()
                .add(Surface.class)
                .build(world);
    }

    public int buildAsteroid(){
        int asteroid = world.create(this.asteroid);
        return asteroid;
    }

    private Bag<Component> getComponents(int entityId){
        return world.getComponentManager().getComponentsFor(entityId, new Bag<>());
    }

}
