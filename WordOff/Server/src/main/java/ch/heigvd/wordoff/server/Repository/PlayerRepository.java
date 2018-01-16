/*
 * File: PlayerRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the player.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
