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
