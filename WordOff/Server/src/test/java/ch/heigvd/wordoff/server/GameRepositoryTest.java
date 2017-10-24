package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.Model.Bag;
import ch.heigvd.wordoff.Model.Game;
import ch.heigvd.wordoff.Repository.GameRepository;
import ch.heigvd.wordoff.Repository.SideRepository;
import ch.heigvd.wordoff.Repository.TileSetRepository;
import ch.heigvd.wordoff.Util.ChallengeFactory;
import ch.heigvd.wordoff.common.Model.Answer;
import ch.heigvd.wordoff.common.Model.Challenge;
import ch.heigvd.wordoff.common.Model.Player;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Slots.*;
import ch.heigvd.wordoff.common.Model.Tiles.TileSet;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class GameRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(GameRepositoryTest.class);

    @Autowired
    private GameRepository repository;

    @Autowired
    private SideRepository sideRepository;

    @Autowired
    private TileSetRepository tilesRepository;

    @Test
    public void testCanCreateAndSaveAGame() throws Exception {
        Game game = new Game("Français");

        // Bag
        TileSet frenchSet = tilesRepository.findByName(game.getLang());
        Bag bag = new Bag(frenchSet.getTiles());
        game.setBag(bag);

        repository.save(game);

        Game savedGame = repository.findOne(1L);
        assertThat(savedGame).isNotNull();
    }

    @Test
    public void testCanCreateAndSaveSide() throws Exception {
        TileSet set = tilesRepository.findByName("Français");
        Bag bag = new Bag(set.getTiles());

        Player player = new Player("testPlayer");
        Side side = new Side(player);

        // Answers
        List<Answer> answers = side.getAnswers();
        answers.add(new Answer(side, (short)1, "Hello", 23));
        answers.add(new Answer(side, (short)2,"World", 32));
        answers.add(new Answer(side, (short)3,"Bye", 14));

        // Racks
        SwapRack swapRack = side.getSwapRack();
        swapRack.addTile(bag.pop());

        PlayerRack playerRack = side.getPlayerRack();
        playerRack.addTile(bag.pop());
        playerRack.addTile(bag.pop());

        // Challenge
        Challenge challenge = new ChallengeFactory(side).addAll(Arrays.asList(
                L2.class,
                Slot.class,
                Swap.class,
                L3.class,
                Slot.class,
                Swap.class,
                SevenTh.class
        )).create();

        challenge.addTile(bag.pop());
        side.setChallenge(challenge);

        sideRepository.save(side);

        Side savedSide = sideRepository.findOne(1L);
        assertThat(savedSide).isNotNull();
    }
}
