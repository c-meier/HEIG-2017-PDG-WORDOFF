package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dto.User.RelationStatus;
import ch.heigvd.wordoff.server.Model.Credentials;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
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

    @Test
    public void testUserCanHaveRelations() throws Exception {
        User one = new User("testOne");
        User two = new User("testTwo");
        User three = new User("testThree");

        one = repository.save(one);
        two = repository.save(two);
        three = repository.save(three);

        assertThat(one).isNotNull();
        assertThat(two).isNotNull();
        assertThat(three).isNotNull();

        one.setRelation(two, RelationStatus.FRIEND);
        one.setRelation(three, RelationStatus.BLOCKED);
        two.setRelation(three, RelationStatus.FRIEND);

        assertThat(one.getRelations().size()).isEqualTo(2);

        one.setRelation(two, RelationStatus.NONE);

        User savedOne = repository.findByName("testOne");
        assertThat(savedOne.getRelations().size()).isEqualTo(1);
    }
}
