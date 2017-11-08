package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.server.Model.Tiles.LangSet;

import javax.persistence.*;
import java.util.Date;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Side sideInit;

    @OneToOne(cascade = CascadeType.ALL)
    private Side sideResp;

    @Lob
    private Bag bag;

    private Date startDate;

    private String lang;

    @ManyToOne
    private Player currPlayer;

    public Game(Player p1, Player p2, LangSet tileSet) {
        this.sideInit = new Side(p1);
        this.sideResp = new Side(p2);
        this.lang = tileSet.getName();
        currPlayer = p1;
        bag = new Bag(tileSet.getTiles());
    }

    public Side getSideOfPlayer(Player player) {
        if (sideInit.getPlayer().equals(player)) {
            return sideInit;
        } else if (sideResp.getPlayer().equals(player)) {
            return sideResp;
        } else {
            /* TODO -> EXCEPTION*/
            return null;
        }
    }

    public Player getOtherPlayer(Player player) {
        if (player.equals(sideInit.getPlayer())) {
            return sideResp.getPlayer();
        } else if (player.equals(sideResp.getPlayer())) {
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

    public Side getSideResp() {
        return sideResp;
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
}
