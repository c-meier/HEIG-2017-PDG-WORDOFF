/*
 * File: PlayerRack.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of the rack of a player
 */
public class PlayerRack extends Rack {

    public PlayerRack() {
        super();
    }

    @Override
    public int getMaxSizeRack() {
        return Constants.PLAYER_RACK_SIZE;
    }

    @Override
    public IRack duplicate() {
        PlayerRack copy = new PlayerRack();
        List<ITile> tilesCopy = new ArrayList<>();
        getTiles().forEach((elem)->tilesCopy.add(elem.duplicate()));
        copy.setTiles(tilesCopy);
        return copy;
    }
}
