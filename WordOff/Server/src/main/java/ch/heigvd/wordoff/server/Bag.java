package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.logic.Tile;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Project : WordOff
 * Date : 10.10.17
 */

@Entity
@Table(name = "Bag")
public class Bag {
    /** LIER A LA BASE DE DONNES
        A, 1, 15
        B, 5, 2
        C, 2, 5
        D, 5, 3
        E, 1, 10
        F, 5, 2
        G, 8, 3
        H, 8, 1
        I, 1, 14
        L, 3, 6
        M, 3, 3
        N, 3, 6
        O, 1, 9
        P, 5, 3
        Q, 10, 1
        R, 2, 9
        S, 2, 7
        T, 2, 7
        U, 3, 3
        V, 5, 3
        Z, 8, 1
     */

    private int id_game;
    private int id_tile;

}
