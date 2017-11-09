package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public CommandLineRunner demo(SideRepository repository, LangSetRepository langSetRepository) {
        return (args) -> {

        };
    }
}
