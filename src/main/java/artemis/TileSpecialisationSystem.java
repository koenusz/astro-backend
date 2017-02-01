package artemis;

import artemis.component.Position;
import artemis.component.Surface;
import artemis.component.Terrain;
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
        Terrain.Specialisation spec = Terrain.Specialisation.Research;

        Surface surface = surfaceComponentMapper.get(entityId);


        for(Terrain terrain : surface.terrainTiles){
            int index = surface.terrainTiles.indexOf(terrain);
            if((index % surface.size.x() == p.x) && (index / surface.size.x() == p.y)){
                terrain.specialisation = spec;
            }

        }
        logger.info("processing entity {} component {} ", entityId, surface);

    }
}
