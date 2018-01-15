package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Abstract class that represent a mode. Every mode will extends this class
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Mode {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The invitations (player) associated with the mode.
     */
    @OneToMany(mappedBy = "pk.mode", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

    /**
     * The games associated whith the mode.
     */
    @OneToMany(mappedBy = "mode")
    private List<Game> games;

    /**
     * The date and time at which the mode has started.
     * For a RANDOM_DUEL corresponds to the moment when there are two player in the mode.
     */
    private LocalDateTime startDate;

    /**
     * The type of the mode.
     */
    private ModeType type;

    /**
     * The 2 letter code of the language of the mode.
     */
    private String lang;

    public Mode() {
        this.invitations = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public ModeType getType() {
        return type;
    }

    public void setType(ModeType type) {
        this.type = type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Get the invitation which concern the given user.
     * @param user The given user.
     * @return The invitation.
     */
    public Invitation getInvitation(User user) {
        return getInvitations()
                .stream()
                .filter(i -> Objects.equals(i.getTarget().getId(), user.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add the given invitation to the list of invitations of the mode. If a matching invitation
     * already exists then update it.
     *
     * The given invitation MUST have this mode as its linked mode.
     *
     * @param invit The given invitation.
     */
    public void putInvitation(Invitation invit) {
        Optional<Invitation> optInvitation = getInvitations()
                .stream()
                .filter(i -> Objects.equals(i.getTarget().getId(), invit.getTarget().getId()))
                .findFirst();
        if(optInvitation.isPresent()) {
            optInvitation.get().setName(invit.getName());
            optInvitation.get().setStatus(invit.getStatus());
        } else {
            invitations.add(invit);
        }
    }

    /**
     * Get the latest active game which concern the given user.
     * @param user The given user.
     * @return An Optional which contains the active game (if it exists).
     */
    public Optional<Game> getActiveGame(User user) {
        return getGames().stream()
                .filter(g -> g.concernPlayer(user))
                .max(Comparator.comparing(Game::getStartDate));
    }

    /**
     * Add the given game to the list of game of the mode.
     * @param game The given game.
     */
    public void addGame(Game game) {
        getGames().add(game);
    }

    /**
     * Check if the mode is ended.
     * @return True if the mode is ended, False otherwise.
     */
    public abstract boolean isEnded();

    /**
     * Get the invitation of the user which has created the mode.
     * @return The invitation of the creator of the mode.
     */
    public Invitation getOriginInvitation() {
        return getInvitations().stream().filter(i -> i.getStatus() == InvitationStatus.ORIGIN).findFirst().get();
    }
}
