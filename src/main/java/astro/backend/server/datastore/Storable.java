package astro.backend.server.datastore;

import com.orientechnologies.orient.core.id.ORID;
import com.sun.istack.internal.Nullable;
import org.immutables.value.Value;

public interface Storable{

    @Nullable
    ORID getId();

    @Value.Default
    default int getVersion(){
        return 0;
    }

    interface StorableBuilder{
        Storable build();

        StorableBuilder from(Storable storable);

        StorableBuilder id(ORID id);

        StorableBuilder version(int version);
    }
}
