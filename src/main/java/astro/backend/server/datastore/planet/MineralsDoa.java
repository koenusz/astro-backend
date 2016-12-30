package astro.backend.server.datastore.planet;


import astro.backend.server.datastore.DoaHelper;
import astro.backend.server.model.planet.components.ImmutableMinerals;
import astro.backend.server.model.planet.components.Minerals;
import com.google.inject.Inject;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.function.BiFunction;

public class MineralsDoa {

    private DoaHelper helper;


    @Inject
    private MineralsDoa(DoaHelper helper) {

        this.helper = helper;
    }

    //TODO pass this method a function to convert a minerals object to a document
    public Minerals saveMinerals(Minerals minerals) {

        Minerals.Builder builder = ImmutableMinerals.builder();

        BiFunction<Minerals, ODocument, ODocument> mapper =
                (min, doc) -> {
                    return doc.field("copper", min.getCopper()).field("iron", min.getIron());
                };

        return (Minerals) helper.save(minerals, builder, mapper);

    }

    public Minerals readMinerals(ORID mineralsId) {

        ImmutableMinerals.Builder builder = ImmutableMinerals.builder();

        ODocument mineralDoc = helper.read(mineralsId, builder);
        if (mineralDoc == null) {
            return null;
        }

        return builder
                .copper(mineralDoc.field("copper"))
                .iron(mineralDoc.field("iron"))
                .build();
    }

    public void deleteMinerals(ORID mineralsId) {
        helper.delete(mineralsId);
    }


}
