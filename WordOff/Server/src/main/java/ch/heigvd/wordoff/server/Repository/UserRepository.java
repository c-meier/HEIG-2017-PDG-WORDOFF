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
