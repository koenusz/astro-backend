package artemis.component;

import com.artemis.Component;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.vavr.collection.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonTypeName("surface")
public class Surface extends Component{


    // 1x1 3x4 4x5 5x6 6x7 7x8
    public enum Size {
        Asteroid(1, 1), Tiny(4, 3), Small(5, 4), Medium(6, 5), Large(7, 6), Huge(8, 7);

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

    public List<Terrain> terrainTiles;
}
