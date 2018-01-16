/*
 * File: SwapRack.java
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
 * Class representation of a rack that will contains the tiles sent by the adversary
 */
public class SwapRack extends Rack {

    public SwapRack() {
        super();
    }

    @Override
    public int getMaxSizeRack() {
        return Constants.SWAP_RACK_SIZE;
    }

    @Override
    public IRack duplicate() {
        SwapRack copy = new SwapRack();
        List<ITile> tilesCopy = new ArrayList<>();
        getTiles().forEach((elem)->tilesCopy.add(elem.duplicate()));
        copy.setTiles(tilesCopy);
        return copy;
    }


}
