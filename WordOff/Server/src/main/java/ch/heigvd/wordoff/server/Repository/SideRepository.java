/*
 * File: SideRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Side;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the sides.
 */
public interface SideRepository extends JpaRepository<Side, Long> {
}
