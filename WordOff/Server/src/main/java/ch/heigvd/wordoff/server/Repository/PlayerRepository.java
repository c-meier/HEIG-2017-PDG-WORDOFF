package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
