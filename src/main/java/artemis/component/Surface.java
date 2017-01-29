package artemis.component;

import com.artemis.Component;
import javaslang.collection.List;


public class Surface extends Component{


    // 1x1 3x4 4x5 5x6 6x7 7x8
    public enum Size {
        Asteroid(1, 1), Tiny(3, 4), Small(4, 5), Medium(5, 6), Large(6, 7), Huge(7, 8);

        private final int x;
        private final int y;


        Size(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

    }


    public Size size;

    public List<Terrain> tiles;
}
