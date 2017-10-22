package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.Model.Bag;
import ch.heigvd.wordoff.Model.Game;
import ch.heigvd.wordoff.Repository.GameRepository;
import ch.heigvd.wordoff.Repository.TileSetRepository;
import ch.heigvd.wordoff.common.logic.TileSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(GameRepositoryTest.class);

    @Autowired
    private GameRepository repository;

    @Autowired
    private TileSetRepository tilesRepository;

    @Test
    public void canHaveABagOfTileTest() throws Exception {
        Game game = new Game("Français");

        TileSet frenchSet = tilesRepository.findByName(game.getLang());

        Bag bag = new Bag();
        bag.setTiles(frenchSet.getTiles());

        game.setBag(bag);

        repository.save(game);

        Game savedGame = repository.findOne(1L);

    }
}
