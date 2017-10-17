package ch.heigvd.wordoff.logic.Racks;

import ch.heigvd.wordoff.logic.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Rack {
    private ObservableList<Tile> tiles = FXCollections.observableArrayList();
    private int sizeRack;

    public Rack() {
    }

    protected void setSize(int sizeRack) {
        this.sizeRack = sizeRack;
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
        if (tiles.size() < sizeRack && !tiles.contains(tile)) {
            tiles.add(tile);
            return true;
        }
        return false;
    }


    protected ObservableList<Tile> getRack() {
        return tiles;
    }

    protected int getSizeRack() {
        return sizeRack;
    }
}
