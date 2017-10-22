package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.logic.TileSet;
import ch.heigvd.wordoff.Repository.TileSetRepository;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TileRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(TileRepositoryTest.class);

    @Autowired
    private TileSetRepository repository;

    @Test
    public void testAutoPopulate() throws Exception {
        Iterable<TileSet> allTileSet = repository.findAll();

        assertThat(allTileSet).isNotEmpty();

        for(TileSet set : allTileSet) {
            log.info("TileSet " + set.getName() + " has " + set.getTiles().size() + " tiles.");
            assertThat(set.getTiles()).isNotEmpty();
        }
    }

}