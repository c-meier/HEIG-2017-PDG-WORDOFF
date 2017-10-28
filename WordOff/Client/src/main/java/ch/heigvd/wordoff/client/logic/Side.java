package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dictionary;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import javafx.beans.property.BooleanProperty;

import java.util.Arrays;
import java.util.List;

public class Side {
    private Player player;
    private Challenge challenge;
    private SwapRack swapRack;
    private PlayerRack playerRack;
    private Game game;
    private BooleanProperty playerTurn;


    public Side(){
        this.playerRack = new PlayerRack();
        this.swapRack = new SwapRack();
        this.challenge = new Challenge(Arrays.asList(1,2,1,3,1,2,5));
    }

    public Player getPlayer(){
        return player;
    }

    public SwapRack getSwapRack() {
        return swapRack;
    }

    public PlayerRack getPlayerRack() {
        return playerRack;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void majRack(List<Tile> newTiles, List<Tile> swapTiles) {
        for (int i = 0; i < newTiles.size(); i++) {
            playerRack.addTile(newTiles.get(i));
        }

        for (int i = 0; i < swapTiles.size(); i++) {
            swapRack.addTile(swapTiles.get(i));
        }
    }

}
