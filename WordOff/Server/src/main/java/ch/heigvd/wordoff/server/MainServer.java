package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.server.Model.Bag;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.server.Model.Answer;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Side;
import ch.heigvd.wordoff.server.Model.Slots.*;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Project : WordOff
 * Date : 26.09.17
 */

@SpringBootApplication
@EntityScan(basePackageClasses = {Constants.class, Game.class})
public class MainServer {

    private static Logger log = LoggerFactory.getLogger(MainServer.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainServer.class, args);
    }

    @PostConstruct
    private void init(){
        log.info("creating an executable jar/war with spring boot without parent pom");
    }

    @Bean
    public CommandLineRunner demo(SideRepository repository, LangSetRepository langSetRepository) {
        return (args) -> {
            LangSet set = langSetRepository.findByName("Français");
            List<Tile> tiles = set.getTiles();
            Bag bag = new Bag(tiles);

            Player player = new Player("testPlayer");
            Side side = new Side(player);

            // Answers
            List<Answer> answers = side.getAnswers();
            answers.add(new Answer(side, (short)1, "Hello", 23));
            answers.add(new Answer(side, (short)2,"World", 32));
            answers.add(new Answer(side, (short)3,"Bye", 14));

            // Challenge
            Challenge challenge = new ChallengeFactory(side).addAll(Arrays.asList(
                    L2Slot.class,
                    Slot.class,
                    SwapSlot.class,
                    L3Slot.class,
                    Slot.class,
                    SwapSlot.class,
                    LastSlot.class
            )).create();

            challenge.addTile(bag.pop());
            side.setChallenge(challenge);

            // Racks
            SwapRack swapRack = side.getChallenge().getSwapRack();
            swapRack.addTile(bag.pop());

            PlayerRack playerRack = side.getPlayerRack();
            playerRack.addTile(bag.pop());
            playerRack.addTile(bag.pop());



            repository.save(side);
        };
    }
}
