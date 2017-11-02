package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import javax.persistence.*;
import java.util.*;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
@Embeddable
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

    protected Bag() {}
    public Bag(List<Tile> tiles) {
        setTiles(new LinkedList<>(tiles));
        shuffle();
    }

    @OneToMany(targetEntity = Tile.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "bag")
    private List<Tile> tiles;

    private void shuffle() {
        Collections.shuffle(getTiles());
    }

    public Tile pop() {
        Tile tmp = getTiles().get(0);
        getTiles().remove(0);
        return tmp;
    }

    public List<Tile> getXTile(int nbTiles) {
        List<Tile> newTiles = new ArrayList<>();
        for (int i = 0; i < nbTiles; i++) {
            newTiles.add(pop());
        }
        return newTiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
