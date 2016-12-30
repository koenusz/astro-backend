package astro.backend.server.datastore.planet;


import astro.backend.server.datastore.DoaHelper;
import astro.backend.server.model.planet.components.*;
import com.google.inject.Inject;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SurfaceDoa {

    private static final Logger logger = LogManager.getLogger();

    private DoaHelper helper;


    @Inject
    private SurfaceDoa(DoaHelper helper) {
        this.helper = helper;
    }

    //TODO pass this method a function to convert a Surface object to a document
    public Surface saveSurface(Surface Surface) {

        Surface.Builder builder = ImmutableSurface.builder();

        BiFunction<Surface, ODocument, ODocument> mapper =
                (sur, doc) -> doc
                                .field("size", new ODocument("Size")
                                    .field("x", sur.getSize().getX())
                                    .field("y", sur.getSize().getY())
                                    , OType.EMBEDDED)
                                .field("tiles",
                                          sur.getTiles()
                                        .stream()
                                        .map( tile -> new ODocument()
                                                        .field("terrainType", tile.getTerrainType())
                                                        .field("position",  new ODocument()
                                                                .field("x", tile.getPosition().getX())
                                                                .field("y", tile.getPosition().getY())
                                                                , OType.EMBEDDED
                                                        )
                                            )
                                        .collect(Collectors.toList())
                                        , OType.EMBEDDEDLIST
                                );



        return (Surface) helper.save(Surface, builder, mapper);
    }

    public Surface readSurface(ORID SurfaceId) {

        ImmutableSurface.Builder builder = ImmutableSurface.builder();



        ODocument surfaceDoc = helper.read(SurfaceId, builder);
        if (surfaceDoc == null) {
            return null;
        }

        logger.info("doc {}", surfaceDoc);

        logger.info("tiles {}", surfaceDoc.field("tiles").toString()   );

        List<ODocument> tiledocs = surfaceDoc.field("tiles");

        List<Tile> tiles = tiledocs
                            .stream()
                            .map(doc -> ImmutableTile
                                        .builder()
                                        .position(ImmutablePosition
                                                .builder()
                                                .x(doc.field("position.x"))
                                                .y(doc.field("position.y"))
                                                .build()
                                        )
                                        .terrainType(Tile.TerrainType.valueOf(doc.field("terrainType")))
                                        .build()
                            )
                            .collect(Collectors.toList());

        return builder
                .size( ImmutableSize.builder()
                        .x(surfaceDoc.field("size.x"))
                        .y(surfaceDoc.field("size.y"))
                        .build()
                )
                .tiles(tiles)
                .build();
    }

    public void deleteSurface(ORID SurfaceId) {
        helper.delete(SurfaceId);
    }


}
