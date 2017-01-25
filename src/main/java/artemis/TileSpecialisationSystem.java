package artemis;

import artemis.component.Position;
import artemis.component.Surface;
import artemis.component.Tile;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileSpecialisationSystem extends IteratingSystem {

    private static final Logger logger = LogManager.getLogger();
    private ComponentMapper<Surface> surfaceComponentMapper;

    /**
     * Creates a new EntityProcessingSystem.
     *
     * @param aspect the aspect to match entities
     */
    public TileSpecialisationSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    @Override
    protected void process(int entityId) {
        //TODO get the event
        // for now assume position 0.0, specialise as research
        Position p = new Position();
        p.x = 0;
        p.y = 0;
        Tile.Specialisation spec = Tile.Specialisation.Research;

        Surface surface = surfaceComponentMapper.get(entityId);
        surface.tiles.filter(tile -> tile.positionX == p.x && tile.positionY == p.y ).get().specialisation = spec;

        logger.info("processing entity {} component {} ", entityId, surface);

    }
}
