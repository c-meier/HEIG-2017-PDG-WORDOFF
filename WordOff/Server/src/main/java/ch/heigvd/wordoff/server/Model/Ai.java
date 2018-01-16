/*
 * File: Ai.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Class that represents an Ai. The application will only have one Ai in the database
 * that will play all the games associated to an Ai.
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Ai extends Player {
    /**
     * Can not create AI, must recuperate from DB.
     */
    public Ai () {super("AI");}
}
