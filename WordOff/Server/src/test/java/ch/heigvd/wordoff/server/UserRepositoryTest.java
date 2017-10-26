package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.Model.Credentials;
import ch.heigvd.wordoff.Model.User;
import ch.heigvd.wordoff.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository repository;

    @Test
    public void testCanSaveCredentials() throws Exception {
        final String LOGIN = "testUser";
        final String PWD = "testPass";
        User user = new User(LOGIN);
        user.setCredentials(new Credentials(LOGIN, PWD.toCharArray()));

        repository.save(user);

        User savedUser = repository.findByCredentialsLogin(LOGIN);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getCredentials().getLogin()).isEqualTo(LOGIN);
    }
}
