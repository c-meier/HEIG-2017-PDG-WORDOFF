package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.ModeRepository;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Mode mode = null;
        Optional<Mode> oMode;

        // add the user asking for a mode
        participantsUsers.add(user);
        for (String str : participants) {
            participantsUsers.add(userRepository.findByName(str));
        }

        // Create mode
        switch (modeType) {
            case FRIEND_DUEL:
                mode = new DuelMode(participantsUsers);
                mode.setType(modeType);
                break;
            case RANDOM_DUEL:
                List<Mode> modeWithOnePlayer = modeRepository.getModesByTypeAndPlayerIsAlone(modeType);

                oMode = modeWithOnePlayer
                        .stream()
                        .filter(m -> m.getOriginInvitation().getTarget().getLevel() == user.getLevel())
                        .findFirst();

                if(!oMode.isPresent()) {
                    mode = new DuelMode(user);
                } else {
                    mode = oMode.get();
                    Invitation originInvitation = mode.getOriginInvitation();
                    mode.putInvitation(new Invitation(mode, user, InvitationStatus.ACCEPT, originInvitation.getTarget().getName()));
                    originInvitation.setName(user.getName());
                }

                mode.setType(modeType);
                break;
            case FRIENDLY_TOURNAMENT:
                if (participantsUsers.size() < TournamentMode.MAX_USER_IN_TOURNAMENT) {
                    mode = new TournamentMode(participantsUsers, name);
                    mode.setType(modeType);
                } else {
                    throw new ErrorCodeException(Protocol.TOO_MANY_PARTICIPANTS, "Too many participants for the tournament (Max. 20).");
                }
                break;
            case COMPETITIVE_TOURNAMENT:
                List<Mode> modeCTournament = modeRepository.findModesByType(modeType);

                oMode = modeCTournament
                        .stream()
                        .filter(m -> m.getOriginInvitation().getTarget().getLevel() == user.getLevel() && m.getInvitations().values().size() < 20)
                        .findFirst();

                if(!oMode.isPresent()) {
                    mode = new TournamentMode(user, Constants.COMPETITION_TOURNAMENT_NAME);
                } else {
                    mode = oMode.get();
                    mode.putInvitation(new Invitation(mode, user, InvitationStatus.ACCEPT, Constants.COMPETITION_TOURNAMENT_NAME));
                }

                mode.setType(modeType);
                break;
        }

        modeRepository.save(mode);

        return mode;
    }
}
