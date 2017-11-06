package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.server.Model.Bag;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Tiles.TileSet;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Repository.TileSetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Daniel on 06.11.2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class GameServiceTest {

    @Autowired
    private GameRepository repository;

    @Autowired
    private SideRepository sideRepository;

    @Autowired
    private TileSetRepository tilesRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Player p1;
    private Player p2;
    private Player ai;

    @Before
    public void setUp() {
        p1 = playerRepository.save(new User("p1"));
        p2 = playerRepository.save(new User("p2"));
        ai = playerRepository.findOne(1L);
    }

    @Test
    public void play() throws Exception {

    }

    @Test
    public void makeAiPLay() throws Exception {

    }

    @Test
    public void initGame() throws Exception {
        TileSet set = tilesRepository.findByName("Français");
        Game game = new Game(p1, p2, set);
        assertThat(game.getBag().getTiles().size()).isEqualTo(103);
    }

}