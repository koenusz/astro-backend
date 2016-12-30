package astro.backend.server.model.planet.components;

import astro.backend.server.datastore.Storable;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Surface extends Storable {

    Size getSize();

    List<Tile> getTiles();

    interface Builder extends StorableBuilder{}
}
