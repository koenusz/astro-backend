package astro.backend.server.datastore.planet;


import astro.backend.server.datastore.AbstractDoa;
import astro.backend.server.datastore.ComponentStore;
import astro.backend.server.datastore.OrientEngineDataStore;
import astro.backend.server.engine.EngineDataStore;
import astro.backend.server.model.planet.components.ImmutableMinerals;
import astro.backend.server.model.planet.components.Minerals;
import com.google.inject.Inject;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.function.BiFunction;

public class MineralsDoa extends AbstractDoa  {


    public Class<Minerals> getType(){
        return Minerals.class;
    }


    @Inject
    public MineralsDoa(OPartitionedDatabasePool pool, EngineDataStore engineDataStore) {
        super(pool);

    }

    public Minerals save(Minerals minerals) {

        Minerals.Builder builder = ImmutableMinerals.builder();

        BiFunction<Minerals, ODocument, ODocument> mapper =
                (min, doc) -> doc.field("copper", min.getCopper()).field("iron", min.getIron());

        return (Minerals) super.save(minerals, builder, mapper);

    }

    public Minerals read(ORID mineralsId) {

        ImmutableMinerals.Builder builder = ImmutableMinerals.builder();

        ODocument mineralDoc = super.read(mineralsId, builder);
        if (mineralDoc == null) {
            return null;
        }

        return builder
                .copper(mineralDoc.field("copper"))
                .iron(mineralDoc.field("iron"))
                .build();
    }

    public void delete(ORID mineralsId) {
        super.delete(mineralsId);
    }




}
