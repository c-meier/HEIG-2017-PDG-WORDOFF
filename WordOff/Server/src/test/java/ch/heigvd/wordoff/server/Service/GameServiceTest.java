package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
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
    private GameRepository repository;

    @Autowired
    private SideRepository sideRepository;

    @Autowired
    private LangSetRepository langSetRepository;

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
        LangSet set = langSetRepository.findByName("Français");
        Game game = new Game(p1, p2, set);

        /* TODO -> set swaprack */
        assertThat(game.getBag().getTiles().size()).isEqualTo(105);

        // Set players Racks
        PlayerRack p1R = game.getSideInit().getPlayerRack();
        PlayerRack p2R = game.getSideResp().getPlayerRack();
        p1R.setTiles(game.getBag().getSevenTiles());
        p2R.setTiles(game.getBag().getSevenTiles());
        game.getSideInit().setChallenge(new ChallengeFactory(game.getSideInit()).createRandomSlotPos().create());
        game.getSideResp().setChallenge(new ChallengeFactory(game.getSideResp()).createRandomSlotPos().create());

        SwapRack s1 = game.getSideInit().getChallenge().getSwapRack();
        SwapRack s2 = game.getSideResp().getChallenge().getSwapRack();
        s1.addTile(game.getBag().pop());
        s1.addTile(game.getBag().pop());
        s2.addTile(game.getBag().pop());
        s2.addTile(game.getBag().pop());

        game.getSideInit().getChallenge().setSwapRack(s1);
        game.getSideResp().getChallenge().setSwapRack(s2);

    }

}
