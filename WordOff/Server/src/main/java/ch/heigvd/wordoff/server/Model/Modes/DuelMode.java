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

    public DuelMode(User participant) {
        putInvitation(new Invitation(this, participant, InvitationStatus.ORIGIN, Constants.NO_ADVERSARY));
    }

    public DuelMode(List<User> participants) {
        User origin = participants.get(0);
        User adversary = participants.get(1);
        putInvitation(new Invitation(this, origin, InvitationStatus.ORIGIN, adversary.getName()));
        putInvitation(new Invitation(this, adversary, InvitationStatus.WAITING, origin.getName()));
    }

    /**
     * Methods to get the first game of the list (and the only one)
     * @return An optional
     */
    public Optional<Game> getGame() {
        return getGames().isEmpty() ? Optional.empty() : Optional.of(getGames().get(0));
    }

    /**
     * Check if the game is finished
     * @return true if the game has ended, else false
     */
    @Override
    public boolean isEnded() {
        return getGame().map(Game::isEnded).orElse(false);
    }
}
