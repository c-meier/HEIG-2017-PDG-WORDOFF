/*
 * File: GameServiceTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.DictionaryLoader;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({GameService.class, DictionaryLoader.class})
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

    @Autowired
    private GameService gameService;

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
        gameWithAi = new Game(new TournamentMode(), p1, ai, set);
    }

    @Test
    public void makeAiPLay() throws Exception {
        initGameWithAI();
        gameWithAi.setCurrPlayer(gameWithAi.getSideResp().getPlayer());

        gameRepository.save(gameWithAi);

        gameService.makeAiPLay(gameWithAi, (User) gameWithAi.getOtherPlayer(ai));
    }

    @Test
    public void initGameWithAI() throws Exception {
        assertThat(gameWithAi.getBag().getTiles().size()).isEqualTo(119);

        Side side1 = gameWithAi.getSideInit();
        Side side2 = gameWithAi.getSideResp();

        // Set players Racks
        PlayerRack p1R = side1.getPlayerRack();
        PlayerRack p2R = side2.getPlayerRack();
        p1R.setTiles(gameWithAi.getBag().getXTile(7));
        p2R.setTiles(gameWithAi.getBag().getXTile(7));
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

        Side savedSide1 = sideRepository.save(side1);
        sideRepository.save(side2);
        gameRepository.save(gameWithAi);

        Side savedSide = sideRepository.findOne(savedSide1.getId());
        assertThat(savedSide).isNotNull();
        Game game1 = gameRepository.findOne(gameWithAi.getId());
        Side sideInit = sideRepository.findOne(gameWithAi.getSideInit().getId());
        Side sideResp = sideRepository.findOne(gameWithAi.getSideResp().getId());
        assertThat(game1).isNotNull();
        assertThat(sideInit).isNotNull();
        assertThat(sideResp).isNotNull();
    }

    @Test
    public void testIfTheACanPlayARightChallenge() throws Exception {
        initGameWithAI();

        Side sideInit = gameWithAi.getSideInit();

        Challenge challenge = new ChallengeFactory(gameWithAi.getSideInit()).createRandomSlotPos().create();
        sideInit.setChallenge(challenge);

        sideInit.setPlayerRack(new PlayerRack());
        sideInit.getPlayerRack().setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new Tile(5, 'a', 1),
                (ITile) new Tile(3, 'b', 3),
                (ITile) new Tile(14, 'c', 3),
                (ITile) new Tile(17, 'd', 2),
                (ITile) new Tile(20, 'e', 2),
                (ITile) new Tile(22, 'f', 4),
                (ITile) new Tile(44, 'i', 1))));
        sideInit.getChallenge().setSwapRack(new SwapRack());
        sideInit.getChallenge().getSwapRack().setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new Tile(4, 'a', 1),
                (ITile) new Tile(45, 'i', 1))));

        gameWithAi.setSideInit(sideInit);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(challenge);
        Challenge challengeClone = mapper.readValue(json, Challenge.class);

        challengeClone.addTile((ITile) new Tile(4, 'a', 1));
        challengeClone.addTile((ITile) new Tile(44, 'i', 1));

        sideRepository.save(sideInit);

        gameService.play(gameWithAi, p1, challengeClone);
    }

}
