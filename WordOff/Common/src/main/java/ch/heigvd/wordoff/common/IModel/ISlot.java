package ch.heigvd.wordoff.common.IModel;

import ch.heigvd.wordoff.common.Model.Tiles.TileDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ISlot {
    ITile getTile();
    void setTile(ITile tile);
    int getScore();

    default boolean isEmpty() {
        if (null == getTile()) {
            return true;
        }
        return false;
    }

    default boolean addTile(ITile tile) {
        if (isEmpty()) {
            setTile(tile);
            return true;
        }
        return false;
    }

    default ITile removeTile() {
        ITile temp = getTile();
        setTile(null);
        return temp;
    }
}
