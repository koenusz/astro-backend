package astro.backend.server.model.planet.components;

import astro.backend.server.datastore.Storable;
import org.immutables.value.Value;

@Value.Immutable
public interface Minerals extends Storable{




    //boolean isMined();

   // String getName();

    int getIron();

    int getCopper();

    interface Builder extends StorableBuilder{}

}
