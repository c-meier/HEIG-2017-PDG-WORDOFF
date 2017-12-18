package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByCredentialsLogin(String login);
    User findByName(String name);
}
