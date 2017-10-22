package ch.heigvd.wordoff.Repository;

import ch.heigvd.wordoff.Model.Game;
import ch.heigvd.wordoff.common.logic.TileSet;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
