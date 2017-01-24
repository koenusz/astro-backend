package artemis.component;

public class Tile {

    enum TerrainType {
        Arctic, Barren, Desert, Forest, Jungle, Mountains, Water
    }

    public TerrainType terrainType;

    public Position position;

}
