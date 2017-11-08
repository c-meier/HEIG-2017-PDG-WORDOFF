package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Side;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
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
    private SideRepository sideRepository;

    @Autowired
    private LangSetRepository langSetRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

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
        LangSet set = langSetRepository.findByName("Français");
        Game game = new Game(p1, ai, set);

        /* TODO -> save swaprack */
        assertThat(game.getBag().getTiles().size()).isEqualTo(105);

        Side side1 = game.getSideInit();
        Side side2 = game.getSideResp();

        // Set players Racks
        PlayerRack p1R = side1.getPlayerRack();
        PlayerRack p2R = side2.getPlayerRack();
        p1R.setTiles(game.getBag().getSevenTiles());
        p2R.setTiles(game.getBag().getSevenTiles());
        side1.setChallenge(new ChallengeFactory(side1).createRandomSlotPos().create());
        side2.setChallenge(new ChallengeFactory(side2).createRandomSlotPos().create());

        SwapRack s1 = side1.getChallenge().getSwapRack();
        SwapRack s2 = side2.getChallenge().getSwapRack();
        s1.addTile(game.getBag().pop());
        s1.addTile(game.getBag().pop());
        s2.addTile(game.getBag().pop());
        s2.addTile(game.getBag().pop());

        side1.getChallenge().setSwapRack(s1);
        side2.getChallenge().setSwapRack(s2);

        sideRepository.save(side1);
        sideRepository.save(side2);
        gameRepository.save(game);

        Side savedSide = sideRepository.findOne(1L);
        assertThat(savedSide).isNotNull();
        Game game1 = gameRepository.findOne(game.getId());
        Side sideInit = sideRepository.findOne(game.getSideInit().getId());
        Side sideResp = sideRepository.findOne(game.getSideResp().getId());
        assertThat(game1).isNotNull();
        assertThat(sideInit).isNotNull();
        assertThat(sideResp).isNotNull();
    }

}
