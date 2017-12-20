package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Modes.Mode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeRepository extends JpaRepository<Mode, Long> {
}
