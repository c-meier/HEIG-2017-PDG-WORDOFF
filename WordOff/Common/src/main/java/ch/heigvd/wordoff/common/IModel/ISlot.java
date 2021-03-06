/*
 * File: ISlot.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.IModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ISlot {
    ITile getTile();
    void setTile(ITile tile);

    Short getPos();
    void setPos(Short pos);

    @JsonIgnore
    int getScore();

    @JsonIgnore
    default boolean isEmpty() {
        return getTile() == null;
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

    ISlot duplicate();
}
