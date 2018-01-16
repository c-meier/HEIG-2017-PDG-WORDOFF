/*
 * File: Bag.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents an Bag object that will contains the tiles in a game
 */
public class Bag implements Serializable {

    protected Bag() {}

    public Bag(List<Tile> tiles) {
        setTiles(new LinkedList<>(tiles));
        shuffle();
    }

    private List<Tile> tiles;

    private void shuffle() {
        Collections.shuffle(getTiles());
    }

    public Tile pop() {
        Tile tmp = getTiles().get(0);
        getTiles().remove(0);
        return tmp;
    }

    public List<ITile> getXTile(int nbTiles) {
        List<ITile> newTiles = new ArrayList<>();
        for (int i = 0; i < nbTiles && !getTiles().isEmpty(); i++) {
            newTiles.add(pop());
        }
        shuffle();
        return newTiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
