/*
 * File: ModeRepositoryTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.InvitationRepository;
import ch.heigvd.wordoff.server.Repository.ModeRepository;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ModeRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(ModeRepositoryTest.class);

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    private User one;
    private User two;
    private DuelMode duelMode;
    private TournamentMode tournament;
    private List<User> users;

    @Before
    public void setUp() {
        one = userRepository.save(new User("testModeRep1"));
        two = userRepository.save(new User("testModeRep2"));
        users = new ArrayList<>();
        users.add(one);
        users.add(two);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(one);
        userRepository.delete(two);
    }

    @Test
    public void testCanCreateAndSaveADuelMode() throws Exception {
        duelMode = new DuelMode(one);

        Mode mode = modeRepository.save(duelMode);
        assertThat(mode).isNotNull();

        duelMode = new DuelMode(users);

        mode = modeRepository.save(duelMode);
        assertThat(mode).isNotNull();
    }

    @Test
    public void testCanCreateAndSaveATournamentMode() throws Exception {
        tournament = new TournamentMode(users, "test");

        Mode mode = modeRepository.save(tournament);
        assertThat(mode).isNotNull();

        tournament = new TournamentMode(one, "test2");

        mode = modeRepository.save(tournament);
        assertThat(mode).isNotNull();
    }
}
