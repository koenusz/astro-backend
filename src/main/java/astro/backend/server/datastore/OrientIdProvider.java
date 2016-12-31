package astro.backend.server.datastore;

import astro.backend.server.engine.IdProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.sequence.OSequence;
import com.orientechnologies.orient.core.metadata.sequence.OSequenceCached;
import com.orientechnologies.orient.core.metadata.sequence.OSequenceLibrary;

@Singleton
public class OrientIdProvider implements IdProvider {


    private OPartitionedDatabasePool pool;

    private OSequenceCached entityIdSequence;
    private OSequenceCached componentIdSequence;

    @Inject
    public OrientIdProvider(OPartitionedDatabasePool pool) {
        this.pool = pool;
        try (ODatabaseDocumentTx db = pool.acquire()) {
            OSequenceLibrary sequenceLibrary = db.getMetadata().getSequenceLibrary();
            entityIdSequence = (OSequenceCached) sequenceLibrary.getSequence("entityIdSequence");
            if (entityIdSequence == null) {
                entityIdSequence = (OSequenceCached) sequenceLibrary.createSequence("entityIdSequence", OSequence.SEQUENCE_TYPE.CACHED,
                        new OSequence.CreateParams()
                                .setCacheSize(20).setStart(0L).setIncrement(1)
                );
            }
            componentIdSequence = (OSequenceCached) sequenceLibrary.getSequence("componentIdSequence");
            if (componentIdSequence == null) {
                componentIdSequence = (OSequenceCached) sequenceLibrary.createSequence("componentIdSequence", OSequence.SEQUENCE_TYPE.CACHED,
                        new OSequence.CreateParams()
                                .setCacheSize(20).setStart(0L).setIncrement(1)
                );
            }
        }
    }

    @Override
    public long getComponentId() {
        try (ODatabaseDocumentTx db = pool.acquire()) {
            return componentIdSequence.next();
        }
    }

    @Override
    public long getEntityId() {
        try (ODatabaseDocumentTx db = pool.acquire()) {
            return entityIdSequence.next();
        }
    }
}
