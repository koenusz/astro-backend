package artemis.component;

import static artemis.component.Terrain.Specialisation.None;

public class Terrain {

    public enum TerrainType {
        Arctic, Barren, Desert, Forest, Jungle, Mountains, Water
    }

    public enum Specialisation{
        Research, Industry, None
    }

    public TerrainType terrainType;

    public int positionX;

    public int positionY;

    //TODO move to colony
    public Specialisation specialisation = None;

}
