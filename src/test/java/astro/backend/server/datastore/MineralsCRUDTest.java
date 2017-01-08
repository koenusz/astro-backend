package astro.backend.server.datastore;

import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.datastore.planet.MineralsDoa;
import astro.backend.server.model.planet.components.ImmutableMinerals;
import astro.backend.server.model.planet.components.Minerals;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MineralsCRUDTest {

    private static final Logger logger = LogManager.getLogger();

    private Minerals mins;
    private Injector injector;


    @BeforeEach
    private void init() {

        injector = Guice.createInjector(new OrientDBModule("memory:crudtest", "admin", "admin"));
        mins = ImmutableMinerals.builder().copper(10).iron(15).build();
    }


    @Test
    public void createTest() {
        MineralsDoa doa = injector.getInstance(MineralsDoa.class);
        Minerals result = doa.save(mins);
        Assertions.assertNotNull(result.getId());
        logger.info(result.toString());
    }

    @Test
    public void readTest() {
        MineralsDoa doa = injector.getInstance(MineralsDoa.class);
        Minerals saved = doa.save(mins);
        Assertions.assertNotNull(saved.getId());
        Minerals read = doa.read(saved.getId());
        Assertions.assertNotNull(read.getId());
        Assertions.assertEquals(saved.getId(), read.getId());
        Assertions.assertEquals(saved, read);


    }

    @Test
    public void updateTest() {
        MineralsDoa doa = injector.getInstance(MineralsDoa.class);
        Minerals saved = doa.save(mins);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(10, saved.getCopper());
        Minerals mins2 = ImmutableMinerals.builder().from(saved).copper(15).build();
        Minerals updated = doa.save(mins2);
        Assertions.assertEquals(15, updated.getCopper());
        Assertions.assertTrue(updated.getVersion() > saved.getVersion());


    }

    @Test
    public void deleteTest() {
        MineralsDoa doa = injector.getInstance(MineralsDoa.class);
        Minerals saved = doa.save(mins);
        Assertions.assertNotNull(saved.getId());

        doa.delete(saved.getId());

        Minerals result = doa.read(saved.getId());
        Assertions.assertNull(result);

    }
}

