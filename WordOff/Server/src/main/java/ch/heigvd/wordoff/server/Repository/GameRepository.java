package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
