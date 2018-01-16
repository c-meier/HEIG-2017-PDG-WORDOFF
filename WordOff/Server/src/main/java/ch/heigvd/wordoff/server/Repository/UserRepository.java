/*
 * File: UserRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the users.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCredentialsLogin(String login);
    User findByName(String name);
}
