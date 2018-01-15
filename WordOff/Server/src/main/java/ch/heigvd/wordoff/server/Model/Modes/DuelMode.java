package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

/**
 * Class representation of a duel between 2 person.
 */
@Entity
public class DuelMode extends Mode {

    public DuelMode() {}

    /**
     * Constructor for a duel where the adversary is not know yet.
     * @param participant The initiator of the duel.
     */
    public DuelMode(User participant) {
        putInvitation(new Invitation(this, participant, InvitationStatus.ORIGIN, Constants.NO_ADVERSARY));
    }

    /**
     * Constructor for a duel where all participants are known at creation.
     *
     * MUST have 2 participants, the first is the initiator and the second is the adversary.
     *
     * @param participants The list of participants.
     */
    public DuelMode(List<User> participants) {
        User origin = participants.get(0);
        User adversary = participants.get(1);
        putInvitation(new Invitation(this, origin, InvitationStatus.ORIGIN, adversary.getName()));
        putInvitation(new Invitation(this, adversary, InvitationStatus.WAITING, origin.getName()));
    }

    /**
     * Methods to get the first game of the list (and the only one)
     *
     * The game is created only when there is two participants to a duel.
     *
     * @return An optional
     */
    public Optional<Game> getGame() {
        return getGames().isEmpty() ? Optional.empty() : Optional.of(getGames().get(0));
    }

    /**
     * Check if the mode is finished
     * @return true if the linked game has ended, else false
     */
    @Override
    public boolean isEnded() {
        return getGame().map(Game::isEnded).orElse(false);
    }
}
