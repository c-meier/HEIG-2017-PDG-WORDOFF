/*
 * File: LangSetRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the langsets.
 */
public interface LangSetRepository extends JpaRepository<LangSet, Integer> {
    LangSet findByName(String name);
}
