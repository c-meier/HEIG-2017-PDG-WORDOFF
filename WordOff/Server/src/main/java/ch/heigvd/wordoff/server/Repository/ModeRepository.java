package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModeRepository extends JpaRepository<Mode, Long> {
    @Query("SELECT m FROM Mode m INNER JOIN m.invitations AS i WHERE m.type = ?1 GROUP BY m HAVING COUNT(i) = 1")
    List<Mode> getModesByTypeAndPlayerIsAlone(ModeType modeType);
    List<Mode> findModesByType(ModeType modeType);
}
