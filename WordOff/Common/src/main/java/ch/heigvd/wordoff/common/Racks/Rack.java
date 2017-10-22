package ch.heigvd.wordoff.common.Racks;

import ch.heigvd.wordoff.common.logic.Tile;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public abstract class Rack {
    private List<Tile> tiles;
    private int maxSizeRack;

    public Rack(int maxSizeRack) {
        this.maxSizeRack = maxSizeRack;
        tiles = new ArrayList<>();
    }

    protected boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * Retirer une tuile du rack
     *
     * @param idTile id de la tuile à retirer
     * @return
     */
    protected Tile getTile(int idTile) {
        for (Tile t : tiles) {
            if (t.getId() == idTile) {
                tiles.remove(t);
                return t;
            }
        }

        return null;
    }

    /**
     * Ajouter une tuile, confirmation.
     *
     * @param tile tuile à ajouter
     */
    protected boolean addTile(Tile tile) {
        if (tiles.size() < maxSizeRack && !tiles.contains(tile)) {
            tiles.add(tile);
            return true;
        }
        return false;
    }

    protected List<Tile> getRack() {
        return tiles;
    }

    protected int getMaxSizeRack() {
        return maxSizeRack;
    }
}
