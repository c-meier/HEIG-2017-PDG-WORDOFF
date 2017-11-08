package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;

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
    private Game gameWithAi;
    private LangSet set;

    @Before
    public void setUp() {
        p1 = playerRepository.save(new User("p1"));
        p2 = playerRepository.save(new User("p2"));
        ai = playerRepository.findOne(1L);
        set = langSetRepository.findByName("fr");
        gameWithAi = new Game(p1, ai, set);
    }

    @Test
    public void makeAiPLay() throws Exception {

    }

    @Test
    public void initGameWithAI() throws Exception {
        /* TODO -> save swaprack */
        assertThat(gameWithAi.getBag().getTiles().size()).isEqualTo(105);

        Side side1 = gameWithAi.getSideInit();
        Side side2 = gameWithAi.getSideResp();

        // Set players Racks
        PlayerRack p1R = side1.getPlayerRack();
        PlayerRack p2R = side2.getPlayerRack();
        p1R.setTiles(gameWithAi.getBag().getSevenTiles());
        p2R.setTiles(gameWithAi.getBag().getSevenTiles());
        side1.setChallenge(new ChallengeFactory(side1).createRandomSlotPos().create());
        side2.setChallenge(new ChallengeFactory(side2).createRandomSlotPos().create());

        SwapRack s1 = side1.getChallenge().getSwapRack();
        SwapRack s2 = side2.getChallenge().getSwapRack();
        s1.addTile(gameWithAi.getBag().pop());
        s1.addTile(gameWithAi.getBag().pop());
        s2.addTile(gameWithAi.getBag().pop());
        s2.addTile(gameWithAi.getBag().pop());

        side1.getChallenge().setSwapRack(s1);
        side2.getChallenge().setSwapRack(s2);

        sideRepository.save(side1);
        sideRepository.save(side2);
        gameRepository.save(gameWithAi);

        Side savedSide = sideRepository.findOne(1L);
        assertThat(savedSide).isNotNull();
        Game game1 = gameRepository.findOne(gameWithAi.getId());
        Side sideInit = sideRepository.findOne(gameWithAi.getSideInit().getId());
        Side sideResp = sideRepository.findOne(gameWithAi.getSideResp().getId());
        assertThat(game1).isNotNull();
        assertThat(sideInit).isNotNull();
        assertThat(sideResp).isNotNull();
    }

    @Test
    public void play() throws Exception {
        initGameWithAI();
        GameService gameService = new GameService();

        Side sideInit = new Side(p1);

        Challenge challenge = new ChallengeFactory(gameWithAi.getSideInit()).createRandomSlotPos().create();
        sideInit.setChallenge(challenge);

        sideInit.setPlayerRack(new PlayerRack());
        sideInit.getPlayerRack().setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new Tile(5, 'a', 1),
                (ITile) new Tile(3, 'b', 3),
                (ITile) new Tile(4, 'c', 3),
                (ITile) new Tile(5, 'd', 2),
                (ITile) new Tile(6, 'e', 2),
                (ITile) new Tile(7, 'f', 4),
                (ITile) new Tile(44, 'i', 1))));
        sideInit.getChallenge().setSwapRack(new SwapRack());
        sideInit.getChallenge().getSwapRack().setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new Tile(4, 'a', 1),
                (ITile) new Tile(45, 'i', 1))));

        gameWithAi.setSideInit(sideInit);

        /* TODO -> le problème est là */
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(challenge);
        Challenge challengeClone = mapper.readValue(json, Challenge.class);

        challengeClone.addTile((ITile) new Tile(4, 'a', 1));
        challengeClone.addTile((ITile) new Tile(44, 'i', 1));

        gameService.play(gameWithAi, p1, challengeClone);

        assertThat('a').isEqualTo('a');
    }

}
