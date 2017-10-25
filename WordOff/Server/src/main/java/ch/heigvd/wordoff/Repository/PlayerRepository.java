package ch.heigvd.wordoff.Repository;

import ch.heigvd.wordoff.common.Model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
