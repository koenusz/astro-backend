package artemis.component.builder;

import artemis.component.*;
import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.google.inject.Inject;
import javaslang.collection.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CelesialBodyBuilder {

    private static final Logger logger = LogManager.getLogger();

    private World world;

    private Archetype star;
    private Archetype planet;
    private Archetype asteroid;

    private ComponentMapper<Position> position;
    private ComponentMapper<Surface> surface;
    private ComponentMapper<CelestialBody> body;

    private int asteroidId = 0;
    private int planetId = 0;


    @Inject
    private CelesialBodyBuilder(World world) {
        this.world = world;
        planet = planet();
        star = star();
        asteroid = asteroid();
        position = world.getMapper(Position.class);
        surface = world.getMapper(Surface.class);
        body = world.getMapper(CelestialBody.class);

    }

    private ArchetypeBuilder celestialBobyArchetype() {
        return new ArchetypeBuilder()
                .add(CelestialBody.class)
                .add(Position.class)
                .add(Subscription.class);

    }

    private Archetype star() {
        return celestialBobyArchetype()
                .build(world);
    }

    public int buildStar() {

        int star = world.create(this.star);
        Position starPosition = position.get(star);
        starPosition.x = 0;
        starPosition.y = 0;
        CelestialBody celestialBody = body.get(star);
        celestialBody.name = "sol";
        celestialBody.type = CelestialBody.Type.Star;
        return star;

    }

    private Archetype planet() {
        return celestialBobyArchetype()
                .add(Surface.class)
                .build(world);
    }

    public int buildPlanet(Surface.Size size, Terrain.TerrainType type, int orbiting, float radius, float angle) {
        int planet = world.create(this.planet);
        Position planetPosition = position.get(planet);
        planetPosition.orbiting = orbiting;
        planetPosition.radius = radius;
        planetPosition.angle = angle;

        Surface planetSurface = surface.get(planet);
        planetSurface.size = size;
        planetSurface.terrainTiles = List.fill(size.x() * size.y(), () -> {
            Terrain terrain = new Terrain();
            terrain.terrainType = type;
            return terrain;
        });


        CelestialBody celestialBody = body.get(planet);
        celestialBody.name = "planet_" + type + "_" + planetId++;

        if (body.get(orbiting).type == CelestialBody.Type.Star) {
            celestialBody.type = CelestialBody.Type.Planet;
        } else {
            celestialBody.type = CelestialBody.Type.Moon;
        }

        return planet;
    }


    private Archetype asteroid() {
        return celestialBobyArchetype()
                .add(Surface.class)
                .build(world);
    }

    public int buildAsteroid(int orbiting, float radius, float angle) {
        int asteroid = world.create(this.asteroid);
        Position asteroidPosition = position.get(asteroid);
        asteroidPosition.orbiting = orbiting;
        asteroidPosition.radius = radius;
        asteroidPosition.angle = angle;

        Surface asteroidSurface = surface.get(asteroid);
        asteroidSurface.size = Surface.Size.Asteroid;
        asteroidSurface.terrainTiles = List.fill(1, () -> {
            Terrain terrain = new Terrain();
            terrain.terrainType = Terrain.TerrainType.Barren;
            return terrain;
        });

        CelestialBody celestialBody = body.get(asteroid);
        celestialBody.name = "asteroid " + asteroidId++;
        celestialBody.type = CelestialBody.Type.Asteroid;


        return asteroid;
    }


}
