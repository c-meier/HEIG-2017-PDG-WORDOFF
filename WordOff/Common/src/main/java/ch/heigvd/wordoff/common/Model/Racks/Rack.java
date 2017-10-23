package ch.heigvd.wordoff.common.Model.Racks;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Embeddable
public abstract class Rack {

    @OneToMany(targetEntity = Tile.class, cascade = CascadeType.ALL)
    private List<Tile> tiles;

    public Rack() {
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
        List<Tile> tiles = getRack();
        if (tiles.size() < getMaxSizeRack() && !tiles.contains(tile)) {
            tiles.add(tile);
            return true;
        }
        return false;
    }

    protected List<Tile> getRack() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    protected abstract int getMaxSizeRack();
}
