package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findAllBySideInitPlayerId(Long id);
    List<Game> findAllBySideRespPlayerId(Long id);

}
