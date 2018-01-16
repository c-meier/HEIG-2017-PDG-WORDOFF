/*
 * File: GameRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for the games.
 */
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllBySideInitPlayerIdOrSideRespPlayerId(Long idInit, Long idResp);

    /**
     * Find all games which concern the given player's id.
     * @param id The player id.
     * @return The list of games which concern the player.
     */
    default List<Game> retrieveAllByOnePlayerId(@Param("playerId") Long id) {
        return findAllBySideInitPlayerIdOrSideRespPlayerId(id, id);
    }
}
