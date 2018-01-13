package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.DictionaryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * Main entry point for the server.
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {Constants.class, MainServer.class})
public class MainServer {

    private static Logger log = LoggerFactory.getLogger(MainServer.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainServer.class, args);
    }

    @Bean
    public DictionaryLoader dictionaryLoader() {
        return new DictionaryLoader();
    }
    
    @PostConstruct
    private void init(){
        log.info("creating an executable jar/war with spring boot without parent pom");
    }
}
