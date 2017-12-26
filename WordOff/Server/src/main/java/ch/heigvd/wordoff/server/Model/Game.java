package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Mode mode;

    @OneToOne
    private Side sideInit;

    @OneToOne
    private Side sideResp;

    @Lob
    private Bag bag;

    private LocalDate startDate;

    private String lang;

    @ManyToOne
    private Player currPlayer;

    private boolean ended;

    public Game() {}

    public Game(Player p1, Player p2, LangSet tileSet) {
        this.sideInit = new Side(p1);
        this.sideResp = new Side(p2);
        this.lang = tileSet.getName();
        currPlayer = p1;
        bag = new Bag(tileSet.getTiles());
        startDate = LocalDate.now();
        ended = false;
    }

    public Side getSideOfPlayer(Player player) {
        if (Objects.equals(sideInit.getPlayer().getId(), player.getId())) {
            return sideInit;
        } else if (Objects.equals(sideResp.getPlayer().getId(), player.getId())) {
            return sideResp;
        } else {
            /* TODO -> EXCEPTION*/
            return null;
        }
    }

    public Player getOtherPlayer(Player player) {
        if (Objects.equals(player.getId(), sideInit.getPlayer().getId())) {
            return sideResp.getPlayer();
        } else if (Objects.equals(player.getId(), sideResp.getPlayer().getId())) {
            return sideInit.getPlayer();
        } else {
            /* TODO -> EXCEPTION */
            return null;
        }
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Side getSideInit() {
        return sideInit;
    }

    public void setSideInit(Side sideInit) {
        this.sideInit = sideInit;
    }

    public Side getSideResp() {
        return sideResp;
    }

    public void setSideResp(Side sideResp) {
        this.sideResp = sideResp;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
