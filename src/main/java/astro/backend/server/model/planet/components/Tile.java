package astro.backend.server.model.planet.components;

import org.immutables.value.Value;

@Value.Immutable
public interface Tile {

    enum TerrainType {
        Arctic, Barren, Desert, Forest, Jungle, Mountains, Water
    }

    Position getPosition();

    TerrainType getTerrainType();
}
