package artemis.component;

import com.artemis.Component;

public class CelestialBody extends Component {

    public enum Type {Star, Asteroid, Moon, Planet}

    public String name;
    public Type type;

}
