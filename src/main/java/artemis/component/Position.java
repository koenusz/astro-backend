package artemis.component;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class Position extends Component{

    public int x;

    public int y;

    public float angle;

    public float radius;

    @EntityId
    public int orbiting;

}
