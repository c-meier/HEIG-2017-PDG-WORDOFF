package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

/**
 * Project : WordOff
 * Date : 26.09.17
 */

@SpringBootApplication
@EntityScan(basePackageClasses = {Constants.class, MainServer.class})
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
