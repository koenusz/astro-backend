package astro.backend.server.datastore;

import com.google.inject.Inject;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.function.BiFunction;

public class DoaHelper {


    private OPartitionedDatabasePool pool;

    @Inject
    public DoaHelper(OPartitionedDatabasePool pool) {
        this.pool = pool;
    }

    public <S extends Storable> Storable save(S storable, Storable.StorableBuilder builder, BiFunction<S, ODocument, ODocument> fieldMapper) {
        try (ODatabaseDocumentTx db = pool.acquire()) {
            ODocument storableDoc;
            if (storable.getId() != null) {
                //update
                storableDoc = new ODocument(storable.getId());
            } else {
                //create
                storableDoc = new ODocument(storable.getClass().getSimpleName());
            }

            storableDoc = fieldMapper.apply(storable, storableDoc);

            storableDoc.save();

            Storable result = builder
                    .from(storable)
                    .id(storableDoc.getIdentity())
                    .version(storableDoc.getVersion())
                    .build();
            return result;
        }
    }

    public ODocument read(ORID id, Storable.StorableBuilder builder) {

        try (ODatabaseDocumentTx db = pool.acquire()) {

            ODocument doc = db.getRecord(id);
            if (doc == null) {
                return null;
            }

            builder.id(doc.getIdentity())
                    .version(doc.getVersion());

            return doc;
        }
    }

    public void delete(ORID id) {
        try (ODatabaseDocumentTx db = pool.acquire()) {
            db.delete(id);
        }
    }
}
