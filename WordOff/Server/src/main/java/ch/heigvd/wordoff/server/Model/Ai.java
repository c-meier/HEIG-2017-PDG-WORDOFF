package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.server.Model.Player;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Created by Daniel on 21.10.2017.
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Ai extends Player {
    /**
     * Can not create AI, must recuperate from DB.
     */
    protected Ai () {}
}
