package netty;

import java.util.List;

public class PlanetMock {

    private Coordinates size;

    public Coordinates getSize() {
        return size;
    }

    public void setSize(Coordinates size) {
        this.size = size;
    }

    private List<Tile> tiles;

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public static class Tile {

        enum Type {Arctic, Barren, Desert, Forest, Jungle, Mountains, Water}

        private Coordinates position;

        public Coordinates getPosition() {
            return position;
        }

        public void setPosition(Coordinates position) {
            this.position = position;
        }

        private Type type;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }

    public static class Coordinates{
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
