package astro.backend.server.datastore;

import astro.backend.server.engine.Component;
import com.orientechnologies.orient.core.id.ORID;

public interface ComponentStore<C extends Component> {

    Class<C> getType();

    C save(C component);

    C read(ORID componentId);

    void delete(ORID componentId);
}
