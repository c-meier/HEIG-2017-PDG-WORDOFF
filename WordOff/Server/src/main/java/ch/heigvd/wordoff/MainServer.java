package ch.heigvd.wordoff;

import ch.heigvd.wordoff.Model.Bag;
import ch.heigvd.wordoff.Model.Game;
import ch.heigvd.wordoff.Repository.SideRepository;
import ch.heigvd.wordoff.Repository.TileSetRepository;
import ch.heigvd.wordoff.Util.ChallengeFactory;
import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Answer;
import ch.heigvd.wordoff.common.Model.Challenge;
import ch.heigvd.wordoff.common.Model.Player;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Slots.*;
import ch.heigvd.wordoff.common.Model.Tiles.TileSet;
import org.hibernate.Hibernate;
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
    public CommandLineRunner demo(SideRepository repository, TileSetRepository tileSetRepository) {
        return (args) -> {
            TileSet set = tileSetRepository.findByName("Français");
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

            repository.save(side);
        };
    }
}
