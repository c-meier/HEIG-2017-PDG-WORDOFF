package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.DictionaryLoader;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({GameService.class, DictionaryLoader.class, ModeService.class})
@ActiveProfiles("test")
public class ModeServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private ModeService modeService;

    @Autowired
    private InvitationRepository invitationRepository;

    private DuelMode duelModeFriendly;
    private DuelMode duelModeRandom;
    private TournamentMode tournamentModeFriendly;
    private TournamentMode tournamentModeRandom;
    private User one, two;
    private List<User> users;
    private List<String> usersStr;
    private Mode mode;

    @Before
    public void setUp() {
        one = userRepository.save(new User("modeServiceTest1"));
        two = userRepository.save(new User("modeServiceTest2"));
        usersStr = new ArrayList<>();
        usersStr.add(two.getName());
        users = new ArrayList<>();
        users.add(two);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(one);
        userRepository.delete(two);
        modeRepository.delete(mode.getId());
    }

    @Test
    public void testCanInitAllModes() {
        mode = modeService.initMode(one, null, usersStr, ModeType.FRIEND_DUEL, "fr");
        assertThat(mode).isNotNull();
        modeRepository.delete(mode.getId());

        mode = modeService.initMode(one, null, null, ModeType.RANDOM_DUEL, "fr");
        assertThat(mode).isNotNull();
        modeRepository.delete(mode.getId());

        mode = modeService.initMode(one, "testFriendlyTournament", usersStr, ModeType.FRIENDLY_TOURNAMENT, "fr");
        assertThat(mode).isNotNull();
        modeRepository.delete(mode.getId());

        mode = modeService.initMode(one, "CompetitiveTournament", null, ModeType.COMPETITIVE_TOURNAMENT, "fr");
        assertThat(mode).isNotNull();
    }

    @Test
    public void testIfRandomDuelAlreadyExistsAddUserToMode() {
        mode = modeService.initMode(one, null, null, ModeType.RANDOM_DUEL, "fr");
        mode = modeService.initMode(two, null, null, ModeType.RANDOM_DUEL, "fr");
        assertThat(mode.getInvitations().size()).isEqualTo(2);
        assertThat(mode.getGames().get(0)).isNotNull();
    }

    @Test
    public void testIfCompetitiveTournamentAlreadyExistsAddUserToMode() {
        mode = modeService.initMode(one, null, null, ModeType.COMPETITIVE_TOURNAMENT, "fr");
        mode = modeService.initMode(two, null, null, ModeType.COMPETITIVE_TOURNAMENT, "fr");
        assertThat(mode.getInvitations().size()).isEqualTo(2);
        assertThat(mode.getGames().size()).isEqualTo(0);
    }
}