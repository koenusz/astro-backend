package astro.backend.server.engine;

import com.orientechnologies.orient.core.id.ORID;

public abstract class AbstractComponent implements Component {

    private final long id;

    private final long entityId;

    public AbstractComponent(long id, long entityId) {
        this.id = id;
        this.entityId = entityId;
    }
//    @Override
//    public long getId() {
//        return id;
//    }

    @Override
    public long getComponentId() {
        return 0;
    }

    @Override
    public long getEntityId() {
        return entityId;
    }


    @Override
    public ORID getId() {
        return null;
    }
}
