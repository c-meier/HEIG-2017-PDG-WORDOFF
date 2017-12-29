package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeRepository extends JpaRepository<Mode, Long> {
    List<Mode> findAllByTypeAndGames_Empty(ModeType modeType);
    List<ModeSummaryDto> findAllByPlayerId();


}
