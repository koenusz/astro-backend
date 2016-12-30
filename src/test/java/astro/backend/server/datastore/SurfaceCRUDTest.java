package astro.backend.server.datastore;

import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.datastore.planet.SurfaceDoa;
import astro.backend.server.model.planet.components.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SurfaceCRUDTest {

    private static final Logger logger = LogManager.getLogger();

    private Surface surface;
    private Injector injector;


    @BeforeEach
    private void init() {

        injector = Guice.createInjector(new OrientDBModule("memory:crudtest", "admin", "admin"));

        Tile tile = ImmutableTile.builder()
                .terrainType(Tile.TerrainType.Forest)
                .position(ImmutablePosition.builder().x(0).y(0).build())
                .build();


        surface = ImmutableSurface.builder().addTiles(tile)
                .size(ImmutableSize.builder().x(1).y(1).build())
                .build();
    }


    @Test
    public void createTest() {
        SurfaceDoa doa = injector.getInstance(SurfaceDoa.class);
        Surface result = doa.saveSurface(surface);
        Assertions.assertNotNull(result.getId());
        logger.info(result.toString());
    }

    @Test
    public void readTest() {
        SurfaceDoa doa = injector.getInstance(SurfaceDoa.class);
        Surface saved = doa.saveSurface(surface);
        Assertions.assertNotNull(saved.getId());
        Surface read = doa.readSurface(saved.getId());
        Assertions.assertNotNull(read.getId());
        Assertions.assertEquals(saved.getId(), read.getId());
        Assertions.assertEquals(saved, read);


    }

    @Test
    public void updateTest() {
        SurfaceDoa doa = injector.getInstance(SurfaceDoa.class);
        Surface saved = doa.saveSurface(surface);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(1, saved.getSize().getX());

        //update the surface
        Surface sur2 = ImmutableSurface.builder()
                .from(saved)
                .size(ImmutableSize.builder().from(saved.getSize()).x(2).build()
                ).addTiles(ImmutableTile.builder()
                        .from(saved.getTiles().get(0))
                        .terrainType(Tile.TerrainType.Desert).build()
                )
                .build();
        Surface updated = doa.saveSurface(sur2);
        Assertions.assertEquals(2, updated.getTiles().size());
        Assertions.assertTrue(updated.getVersion() > saved.getVersion());


    }

    @Test
    public void deleteTest() {
        SurfaceDoa doa = injector.getInstance(SurfaceDoa.class);
        Surface saved = doa.saveSurface(surface);
        Assertions.assertNotNull(saved.getId());

        doa.deleteSurface(saved.getId());

        Surface result = doa.readSurface(saved.getId());
        Assertions.assertNull(result);

    }
}

