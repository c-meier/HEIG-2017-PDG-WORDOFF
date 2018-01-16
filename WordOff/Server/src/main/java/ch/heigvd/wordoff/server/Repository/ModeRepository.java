/*
 * File: ModeRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for the modes.
 */
public interface ModeRepository extends JpaRepository<Mode, Long> {
    /**
     * Get a list of mode of the given type which have only one participant.
     * @param modeType The given type.
     * @return The list of modes.
     */
    @Query("SELECT m FROM Mode m INNER JOIN m.invitations AS i WHERE m.type = ?1 GROUP BY m HAVING COUNT(i) = 1")
    List<Mode> getModesByTypeAndPlayerIsAlone(ModeType modeType);
    List<Mode> findModesByType(ModeType modeType);
}
