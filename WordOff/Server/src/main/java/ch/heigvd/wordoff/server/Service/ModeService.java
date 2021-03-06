/*
 * File: ModeService.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that represents the service for the modes
 */
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
        if (participants != null) {
            participantsUsers.add(user);
            for (String str : participants) {
                User participant = userRepository.findByName(str);
                if (participant != null) {
                    participantsUsers.add(userRepository.findByName(str));
                }
            }
        }

        // Create mode
        switch (modeType) {
            case FRIEND_DUEL:
                mode = new DuelMode(participantsUsers);
                mode.setType(modeType);
                mode.setLang(lang);
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
                    mode.setLang(lang);
                } else if (!oMode.get().getOriginInvitation().getTarget().getId().equals(user.getId())) {
                    mode = oMode.get();
                    Invitation originInvitation = mode.getOriginInvitation();
                    mode.putInvitation(new Invitation(mode, user, InvitationStatus.ACCEPT, originInvitation.getTarget().getName()));
                    originInvitation.setName(user.getName());

                    // Start the game directly
                    gameService.initGame(mode, originInvitation.getTarget(), user);
                    mode.setStartDate(LocalDateTime.now());
                } else {
                    return oMode.get();
                }
                break;
            case FRIENDLY_TOURNAMENT:
                if (participantsUsers.size() < Constants.MAX_USER_IN_TOURNAMENT) {
                    mode = new TournamentMode(participantsUsers, name);
                    mode.setType(modeType);
                    mode.setStartDate(LocalDateTime.now());
                    mode.setLang(lang);
                } else {
                    throw new ErrorCodeException(Protocol.TOO_MANY_PARTICIPANTS, "Too many participants for the tournament (Max. 20).");
                }
                break;
            case COMPETITIVE_TOURNAMENT:
                List<Mode> modeCTournament = modeRepository.findModesByType(modeType);

                oMode = modeCTournament
                        .stream()
                        .filter(m -> m.getOriginInvitation().getTarget().getLevel() == user.getLevel() &&
                                     m.getInvitations().size() < Constants.MAX_USER_IN_TOURNAMENT &&
                                     m.getLang().equals(lang) &&
                                     Duration.between(LocalDateTime.now(), m.getStartDate()).toHours() <= Constants.MAX_HOURS_ELAPSED_IN_TOURNAMENT_FOR_PLAYER_TO_JOIN_MODE)
                        .findFirst();

                if(!oMode.isPresent()) {
                    mode = new TournamentMode(user, Constants.COMPETITION_TOURNAMENT_NAME);
                    mode.setType(modeType);
                    mode.setStartDate(LocalDateTime.now());
                    mode.setLang(lang);
                } else if (oMode.get().getInvitations().stream().noneMatch(i -> i.getTarget().getId().equals(user.getId()))) {
                    mode = oMode.get();
                    mode.putInvitation(new Invitation(mode, user, InvitationStatus.ACCEPT, Constants.COMPETITION_TOURNAMENT_NAME));
                } else {
                    return oMode.get();
                }
                break;
        }

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

        int nbGameDoneForCurrDay = (int) mode.getGames().stream()
                .filter(g -> g.getSideInit().getPlayer().getName().equals(player.getName()))
                .filter(Game::isEnded)
                .filter(g -> Duration.between(g.getStartDate(), mode.getStartDate()).toDays() ==
                             Duration.between(LocalDateTime.now(), mode.getStartDate()).toDays())
                .count();

        if (nbGameDoneForCurrDay >= 2) {
            throw new ErrorCodeException(Protocol.TOO_MUCH_GAMES_FOR_DAY_X, "The player have already done 2 games for the current day.");
        }

        switch (mode.getType()) {
            case FRIEND_DUEL:
                User origin = mode.getOriginInvitation().getTarget();
                User adversary = mode.getInvitations()
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
