package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import java.util.ArrayList;

public class Bag {
    private int numberTiles;

    public Bag(int numberTiles){
        this.numberTiles = numberTiles;
    }

    public ArrayList<Tile> getTiles(int number){
        ArrayList<Tile> tiles = new ArrayList<>();
        if(number > numberTiles) {
            number = numberTiles;
        }
        // requête au serveur
        // construction de tiles

        // Comptabilise le retrait si requête en ordre
        numberTiles -= tiles.size();
        return tiles;
    }

    public int getNumberTiles(){
        return numberTiles;
    }
}
