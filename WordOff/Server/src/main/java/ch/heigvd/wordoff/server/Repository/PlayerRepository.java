package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
