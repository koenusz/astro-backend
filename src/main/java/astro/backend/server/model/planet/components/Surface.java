package astro.backend.server.model.planet.components;

import astro.backend.server.engine.Component;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Surface extends Component {

    Size getSize();

    List<Tile> getTiles();

    interface Builder extends ComponentBuilder{}
}
