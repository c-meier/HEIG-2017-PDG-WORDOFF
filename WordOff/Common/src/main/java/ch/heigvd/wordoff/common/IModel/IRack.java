package ch.heigvd.wordoff.common.IModel;

import java.util.List;

public interface IRack {
    List<ITile> getTiles();

    int getMaxSizeRack();

    /**
     * Retirer une tuile du rack
     *
     * @param idTile id de la tuile à retirer
     * @return
     */
    default ITile getTile(int idTile) {
        List<ITile> tiles = getTiles();

        for (ITile t : tiles) {
            if (t.getId() == idTile) {
                tiles.remove(t);
                return t;
            }
        }

        return null;
    }

    /**
     * Get tile by position in rack
     * @param pos
     * @return
     */
    default ITile getTileByPos(int pos){
        List<ITile> tiles = getTiles();

        if(pos < tiles.size()){
            return tiles.remove(pos);
        }

        return null;
    }

    /**
     * Ajouter une tuile, confirmation.
     *
     * @param tile tuile à ajouter
     */
    default boolean addTile(ITile tile) {
        List<ITile> tiles = getTiles();
        if (tiles.size() < getMaxSizeRack() && !tiles.contains(tile)) {
            tiles.add(tile);
            return true;
        }
        return false;
    }


    default boolean isEmpty() {
        return getTiles().isEmpty();
    }
}
