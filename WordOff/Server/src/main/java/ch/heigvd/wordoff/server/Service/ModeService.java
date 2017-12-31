package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.InvitationRepository;
import ch.heigvd.wordoff.server.Repository.ModeRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeService {
    private UserRepository userRepository;
    private ModeRepository modeRepository;
    private GameService gameService;
    private InvitationRepository invitationRepository;
    private PlayerRepository playerRepository;

    public ModeService(GameService gameService, ModeRepository modeRepository, UserRepository userRepository, InvitationRepository invitationRepository, PlayerRepository playerRepository) {
        this.gameService = gameService;
        this.modeRepository = modeRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Initialization of a mode :
     * - FRIEND_DUEL -> duel with a friend (need to wait for him to accept the challenge)
     * - RANDOM_DUEL -> try to find an adversary to play a game (if a game is found, the game is initialize directly)
     * - FRIENDLY_TOURNAMENT -> Tournament with friends
     * - COMPETITIVE_TOURNAMENT -> Tournament with random people
     * @param user The user
     * @param name The Tournament name
     * @param participants The list of participants (without the user)
     * @param modeType The type of the mode (look up for the possibilities)
     * @param lang The language used for this mode
     * @return The mode created or if a mode is available for RANDOM_DUEL or COMPETITIVE_TOURNAMENT,
     * return the first available mode
     */
    public Mode initMode(User user, String name, List<String> participants, ModeType modeType, String lang) {
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
                // Get a list of mode that are of the type RANDOM_DUEL with only 1 player
                List<Mode> modeWithOnePlayer = modeRepository.getModesByTypeAndPlayerIsAlone(modeType);

                // Get the first optional where the player have the same level as the user
                oMode = modeWithOnePlayer
                        .stream()
                        .filter(m -> m.getOriginInvitation().getTarget().getLevel() == user.getLevel() &&
                                     m.getLang().equals(lang))
                        .findFirst();

                if(!oMode.isPresent()) {
                    mode = new DuelMode(user);
                    mode.setType(modeType);
                } else {
                    mode = oMode.get();
                    Invitation originInvitation = mode.getOriginInvitation();
                    mode.putInvitation(new Invitation(mode, user, InvitationStatus.ACCEPT, originInvitation.getTarget().getName()));
                    originInvitation.setName(user.getName());

                    // Start the game directly
                    gameService.initGame(mode, originInvitation.getTarget(), user);
                    mode.setStartDate(LocalDateTime.now());
                }
                break;
            case FRIENDLY_TOURNAMENT:
                if (participantsUsers.size() < Constants.MAX_USER_IN_TOURNAMENT) {
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
                        .filter(m -> m.getOriginInvitation().getTarget().getLevel() == user.getLevel() &&
                                     m.getInvitations().values().size() < Constants.MAX_USER_IN_TOURNAMENT &&
                                     m.getLang().equals(lang))
                        .findFirst();

                if(!oMode.isPresent()) {
                    mode = new TournamentMode(user, Constants.COMPETITION_TOURNAMENT_NAME);
                    mode.setType(modeType);
                } else {
                    mode = oMode.get();
                    mode.putInvitation(new Invitation(mode, user, InvitationStatus.ACCEPT, Constants.COMPETITION_TOURNAMENT_NAME));
                }
                break;
        }

        mode.setLang(lang);

        modeRepository.save(mode);

        return mode;
    }

    /**
     * Modify the status of an invitation in a mode and init a game if the response is ACCEPT or
     * delete the mode (for FRIEND_DUEL) if the response is DENY
     * @param modeId Id of the mode
     * @param player The player that change his invitation status
     * @param invitationStatus The new status of the invitation
     */
    public void changeInvitationStatus(Long modeId, User player, InvitationStatus invitationStatus) {
        switch(invitationStatus) {
            case ACCEPT:
            case DENY:
                Invitation invitation = invitationRepository.findByPkTargetIdAndPkModeId(player.getId(), modeId);
                invitation.setStatus(invitationStatus);
                invitationRepository.saveAndFlush(invitation);
                break;
            case ORIGIN:
            case WAITING:
                throw new ErrorCodeException(Protocol.INVALID_INVITATION_STATUS, "You can not change the invitation status to ORIGIN or WAITING");
        }

        Mode mode = modeRepository.findOne(modeId);
        if(invitationStatus == InvitationStatus.ACCEPT) {
            initModeGame(modeId, player);
        } else {
            if (mode.getType() == ModeType.FRIEND_DUEL) {
                modeRepository.delete(modeId);
            }
        }

    }

    /**
     * Initialize a game for a mode
     * @param modeId The id of the mode
     * @param player The player who wants to start the game
     * @return The new game
     */
    public Game initModeGame(Long modeId, User player) {
        Mode mode = modeRepository.findOne(modeId);
        Game game = null;

        switch (mode.getType()) {
            case FRIEND_DUEL:
                User origin = mode.getOriginInvitation().getTarget();
                User adversary = mode.getInvitations().values()
                        .stream()
                        .filter(i -> i.getStatus() == InvitationStatus.ACCEPT)
                        .findFirst()
                        .get()
                        .getTarget();
                game = gameService.initGame(mode, origin, adversary);
                mode.setStartDate(LocalDateTime.now());
                break;
            case RANDOM_DUEL:
                break;
            case FRIENDLY_TOURNAMENT:
            case COMPETITIVE_TOURNAMENT:
                Player ai = playerRepository.findOne(1L);
                game = gameService.initGame(mode, player, ai);
                mode.setStartDate(LocalDateTime.now());
                break;
        }

        modeRepository.save(mode);

        return game;
    }

    /**
     * Get the mode in the database
     * @param modeId Id of the mode
     * @return the mode
     */
    public Mode getMode(Long modeId) {
        return modeRepository.findOne(modeId);
    }
}
