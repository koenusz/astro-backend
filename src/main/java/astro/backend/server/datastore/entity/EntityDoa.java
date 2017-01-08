package astro.backend.server.datastore.entity;

import astro.backend.server.datastore.AbstractDoa;
import astro.backend.server.engine.Entity;
import com.google.inject.Inject;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;

public class EntityDoa extends AbstractDoa {


    @Override
    public Class getType() {
        return Entity.class;
    }

    @Inject
    public EntityDoa(OPartitionedDatabasePool pool) {
        super(pool);
    }




}
