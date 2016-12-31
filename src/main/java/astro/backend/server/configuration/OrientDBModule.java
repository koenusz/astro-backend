package astro.backend.server.configuration;

import astro.backend.server.datastore.OrientIdProvider;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.IdProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrientDBModule extends AbstractModule {

    private static final Logger logger = LogManager.getLogger();
    private final OPartitionedDatabasePool pool;

    public OrientDBModule(String url, String userName, String password) {
        super();
        logger.debug(url);
        pool = new OPartitionedDatabasePool(url, userName, password);
        pool.setAutoCreate(true);
    }

    @Provides
    public OPartitionedDatabasePool providesPool() {
        return pool;
    }

    @Override
    protected void configure() {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            db.getEntityManager().registerEntityClasses("astro.backend.server.model.components");
        }
        bind(IdProvider.class).to(OrientIdProvider.class);
    }

//    @Override
//    protected void configure() {
////        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
//
//            db.getEntityManager().registerEntityClass(PersistentEntity.class);
//            db.getEntityManager().registerEntityClasses("com.space.spacesim.model.util.component");
//            db.getEntityManager().registerEntityClasses("com.space.spacesim.model.common.component");
//            db.getEntityManager().registerEntityClasses("com.space.spacesim.model.ship.component");
//
//            addCluster(Ship.class.getSimpleName());
//        }
//    }

//    private void addCluster(String clusterName) {
//        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
//            if (db.getClusterIdByName(clusterName) == -1) {
//                db.addCluster(clusterName);
//                db.getMetadata().getSchema().getClass(PersistentEntity.class).addCluster(clusterName);
//            }
//        }
//    }


}

