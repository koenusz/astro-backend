package artemis.component;

import static artemis.component.Tile.Specialisation.None;

public class Tile {

    public enum TerrainType {
        Arctic, Barren, Desert, Forest, Jungle, Mountains, Water
    }

    public enum Specialisation{
        Research, Industry, None
    }

    public TerrainType terrainType;

    public int positionX;

    public int positionY;

    public Specialisation specialisation = None;

}
