/*
 * File: SwapSlot.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model.Slots;

/**
 * Class that represents a slot on which the player can put a tile that will be sent to the opponent
 */
public class SwapSlot extends Slot {

    public SwapSlot() {

    }

    public SwapSlot(Short pos) {
        super(pos);
    }
}
