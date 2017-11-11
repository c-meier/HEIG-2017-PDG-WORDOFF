package ch.heigvd.wordoff.server.Model;

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
    public Ai () {super("AI");}
}
