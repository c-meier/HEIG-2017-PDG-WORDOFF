package ch.heigvd.wordoff.Model;

import ch.heigvd.wordoff.common.Model.Player;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Created by Daniel on 21.10.2017.
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Ai extends Player {
    private int level;

    public Ai(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}