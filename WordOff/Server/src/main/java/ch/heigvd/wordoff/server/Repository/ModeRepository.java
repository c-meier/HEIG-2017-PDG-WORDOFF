package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeRepository extends JpaRepository<Mode, Long> {
    List<Mode> findAllByPlayerId(@Param("playerId") Long id);
    List<Mode> findAllByTypeAndInvitations(ModeType modeType);
}
