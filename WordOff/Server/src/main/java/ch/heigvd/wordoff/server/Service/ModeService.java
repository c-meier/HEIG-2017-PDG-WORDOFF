package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.ModeRepository;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModeService {
    private UserRepository userRepository;
    private ModeRepository modeRepository;
    private GameService gameService;

    public ModeService(GameService gameService, ModeRepository modeRepository, UserRepository userRepository) {
        this.gameService = gameService;
        this.modeRepository = modeRepository;
        this.userRepository = userRepository;
    }

    public Mode initMode(User user, String name, List<String> participants, ModeType modeType) {
        List<User> participantsUsers = new ArrayList<>();
        // add the user asking for a mode
        participantsUsers.add(user);
        for (String str : participants) {
            participantsUsers.add(userRepository.findByName(str));
        }
        switch (modeType) {
            case FRIEND_DUEL:
                Mode mode = new DuelMode(participantsUsers);
                break;
            case RANDOM_DUEL:
                List<Mode> modeWithNullGame = modeRepository.findAllByTypeAndGames_Empty(modeType);
                if (modeWithNullGame
                        .stream()
                        .filter((DuelMode m) -> m.getInvitations().values()
                                .forEach(invitation -> {
                                    invitation.getTarget().getLevel() == user.getLevel();
                                }) {

                }
            case FRIENDLY_TOURNAMENT:
            case COMPETITIVE_TOURNAMENT:
            default:


        }
    }

    public Mode initModeDuel(User user, Mode mode) {

    }
}
