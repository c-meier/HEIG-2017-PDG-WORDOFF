package ch.heigvd.wordoff.logic;

import java.util.ArrayList;

public class Bag {
    private final int numberTilesMax = 115;
    private int numberTiles;

    public Bag(){
        this.numberTiles = numberTilesMax;
    }

    public ArrayList<Tile> getTiles(int number){
        ArrayList<Tile> tiles = new ArrayList<>();
        if(number > numberTiles)
            number = numberTiles;

        // requête au serveur
        // construction de tiles
        return tiles;
    }
}
