package astro.backend.server.engine;

public abstract class AbstractComponent implements Component {

    private final long id;

    private final long entityId;

    public AbstractComponent(long id, long entityId) {
        this.id = id;
        this.entityId = entityId;
    }
    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getEntityId() {
        return entityId;
    }


}
