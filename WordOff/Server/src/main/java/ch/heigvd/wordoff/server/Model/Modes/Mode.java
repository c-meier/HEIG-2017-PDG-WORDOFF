package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Mode {
    @Id
    private Long id;

    @OneToMany(mappedBy = "pk.mode")
    @MapKey(name = "pk.target")
    private Map<Long, Invitation> invitations;

    @OneToMany(mappedBy = "mode")
    private List<Game> games;

    private LocalDate startDate;

    private ModeType type;

    public Mode() {
        this.invitations = new TreeMap<>();
        this.games = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Map<Long, Invitation> invitations) {
        this.invitations = invitations;
    }

    public void putInvitation(Invitation invit) {
        invitations.put(invit.getTarget().getId(), invit);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ModeType getType() {
        return type;
    }

    public void setType(ModeType type) {
        this.type = type;
    }
}
