package ch.heigvd.wordoff.Repository;

import ch.heigvd.wordoff.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByCredentialsLogin(String login);
}
