package ch.heigvd.wordoff;

import ch.heigvd.wordoff.Model.Game;
import ch.heigvd.wordoff.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.annotation.PostConstruct;

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
}
