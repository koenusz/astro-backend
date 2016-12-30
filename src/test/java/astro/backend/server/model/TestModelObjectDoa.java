package astro.backend.server.model;

import astro.backend.server.model.planet.components.ImmutableMinerals;
import astro.backend.server.model.planet.components.Minerals;
import com.google.inject.Inject;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class TestModelObjectDoa {

    private OPartitionedDatabasePool pool;


    @Inject
    private TestModelObjectDoa(OPartitionedDatabasePool pool) {
        this.pool = pool;
    }

//    public Minerals saveTest(TestModelObject minerals) {
//        ODatabaseDocumentTx db = pool.acquire();
//        ODocument mineralDoc = copyFields(minerals);
//        mineralDoc.save();
//       // Minerals result = ImmutableTe.builder().from(minerals).id(mineralDoc.getIdentity()).build();
//        db.close();
//        return result;
//    }

    private ODocument copyFields(TestModelObject test) {
        ODocument testDoc;
        if (test.getId() != null) {
            testDoc = new ODocument(test.getId());
        } else {
            testDoc = new ODocument("TestModelObject");
        }
        testDoc.field("byte", test.getByte());
        testDoc.field("short", test.getShort());
        testDoc.field("int", test.getInt());
        testDoc.field("long", test.getLong());
        testDoc.field("float", test.getFloat());
        testDoc.field("double", test.getDouble());
        testDoc.field("boolean", test.isBool());
        testDoc.field("char", test.getChar());
        testDoc.field("string", test.getString());

        return testDoc;
    }

    public Minerals readMinerals(ORID mineralsId) {
        ODatabaseDocumentTx db = pool.acquire();
        ODocument mineralDoc = db.getRecord(mineralsId);

        Minerals result = ImmutableMinerals.builder()
                .id(mineralDoc.getIdentity())
                .copper(mineralDoc.field("copper"))
                .iron(mineralDoc.field("iron"))
                .build();
        db.close();
        return result;
    }

//    public Minerals updateMinerals(Minerals minerals) {
//        ODatabaseDocumentTx db = pool.acquire();
//        ODocument dbInstance = new ODocument(minerals.getId());
//
//        ODocument mineralDoc = copyFields(minerals);
//        mineralDoc.save();
//        Minerals result = ImmutableMinerals.builder().from(minerals).id(mineralDoc.getIdentity()).build();
//        db.close();
//        return result;
//    }

//    public Minerals deleteMinerals(ORID mineralsId) {
//        ODatabaseDocumentTx db = pool.acquire();
//        ODocument mineralDoc = new ODocument("Minerals");
//        mineralDoc.field("copper", minerals.getCopper());
//        mineralDoc.field("iron", minerals.getIron());
//        mineralDoc.save();
//        Minerals result = ImmutableMinerals.builder().from(minerals).id(mineralDoc.getIdentity()).build();
//        db.close();
//        return result;
//    }


}
