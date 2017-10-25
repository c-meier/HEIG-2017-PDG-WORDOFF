package ch.heigvd.wordoff.Model;

import ch.heigvd.wordoff.common.Model.Player;
import ch.heigvd.wordoff.common.Model.Side;

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

    @Embedded
    private Bag bag;

    private Date startDate;

    private String lang;

    @ManyToOne
    private Player currPlayer;

    public Game(Player p1, Player p2, String lang) {
        this.sideInit = new Side(p1);
        this.sideResp = new Side(p2);
        this.lang = lang;
        currPlayer = p1;
        bag = new Bag();
    }

    public Side getSideOfPlayer(Player player) {
        if (sideInit.getPlayer().equals(player)) {
            return sideInit;
        } else if (sideResp.getPlayer().equals(player)) {
            return sideResp;
        } else {
            /* TO DO -> EXCEPTION*/
            return null;
        }
    }

    public Player getOtherPlayer(Player player) {
        if (player.equals(sideInit.getPlayer())) {
            return sideResp.getPlayer();
        } else if (player.equals(sideResp.getPlayer())) {
            return sideInit.getPlayer();
        } else {
            /* TO DO -> EXCEPTION */
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

}
