package astro.backend.server.datastore.planet;


import astro.backend.server.datastore.AbstractDoa;
import astro.backend.server.datastore.ComponentStore;
import astro.backend.server.datastore.OrientEngineDataStore;
import astro.backend.server.model.planet.components.*;
import com.google.inject.Inject;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SurfaceDoa extends AbstractDoa implements ComponentStore<Surface> {


    public Class<Surface> getType() {
        return Surface.class;
    }

    @Inject
    public SurfaceDoa(OPartitionedDatabasePool pool) {
        super(pool);
    }

    //TODO pass this method a function to convert a Surface object to a document
    public Surface save(Surface Surface) {

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
                                        .map(tile -> new ODocument()
                                                .field("terrainType", tile.getTerrainType())
                                                .field("position", new ODocument()
                                                                .field("x", tile.getPosition().getX())
                                                                .field("y", tile.getPosition().getY())
                                                        , OType.EMBEDDED
                                                )
                                        )
                                        .collect(Collectors.toList())
                                , OType.EMBEDDEDLIST
                        );


        return (Surface) super.save(Surface, builder, mapper);
    }

    public Surface read(ORID SurfaceId) {

        ImmutableSurface.Builder builder = ImmutableSurface.builder();

        ODocument surfaceDoc = super.read(SurfaceId, builder);
        if (surfaceDoc == null) {
            return null;
        }

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
                .size(ImmutableSize.builder()
                        .x(surfaceDoc.field("size.x"))
                        .y(surfaceDoc.field("size.y"))
                        .build()
                )
                .tiles(tiles)
                .build();
    }

    public void delete(ORID SurfaceId) {
        super.delete(SurfaceId);
    }


}
