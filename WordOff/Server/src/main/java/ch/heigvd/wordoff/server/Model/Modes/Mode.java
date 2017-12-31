package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Mode {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "pk.mode", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

    @OneToMany(mappedBy = "mode")
    private List<Game> games;

    private LocalDateTime startDate;

    private ModeType type;

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

    public Invitation getInvitation(User user) {
        return getInvitations()
                .stream()
                .filter(i -> Objects.equals(i.getTarget().getId(), user.getId()))
                .findFirst()
                .orElse(null);
    }

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

    public List<Game> getGames() {
        return games;
    }

    public Optional<Game> getActiveGame(User user) {
        return getGames().stream()
                .filter(g -> g.concernPlayer(user))
                .max(Comparator.comparing(Game::getStartDate));
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        getGames().add(game);
    }

    public abstract boolean isEnded();

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

    public Invitation getOriginInvitation() {
        return getInvitations().stream().filter(i -> i.getStatus() == InvitationStatus.ORIGIN).findFirst().get();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
