package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Cases.SlotType;
import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dictionary;
import ch.heigvd.wordoff.common.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Racks.SwapRack;
import ch.heigvd.wordoff.common.logic.Tile;
import javafx.beans.property.BooleanProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class Side {
    private Player player;
    private Challenge challenge;
    private SwapRack swapRack;
    private PlayerRack playerRack;
    private BooleanProperty playerTurn;
    private BooleanProperty walsActive;
    private int score;

    public Side(Player player, ArrayList<SlotType> slots, Dictionary dico) {
        swapRack = new SwapRack();
        playerRack = new PlayerRack();
        this.player = player;
        challenge = new Challenge(slots, dico);
        score = 0;
    }

    /**
     * @brief check if the word exist
     * @return true if the word is valid, else false
     */
    public boolean checkWord() {
        return challenge.checkWord();
    }

    /**
     * @brief Calculate the score fro this side
     */
    public void calculateScore() {
        score += challenge.getScoreWord();
    }

    /**
     * @brief Update the side rack with
     * @param newTiles The list of tiles to add to the player rack
     * @param swapTiles The list of tiles to add to the swap rack if this side
     */
    public void updateRack(List<Tile> newTiles, List<Tile> swapTiles) {
        for (int i = 0; i < newTiles.size(); i++) {
            playerRack.addTile(newTiles.get(i));
        }

        for (int i = 0; i < swapTiles.size(); i++) {
            swapRack.addTile(swapTiles.get(i));
        }
    }

    public void updateChallenge(ArrayList<SlotType> slots, Dictionary dico) {
        /* TO DISCUSS : Do we clear only the slots after accepting the word or do we create a new challenge ? */
        // challenge = new Challenge(slots, dico);
    }

    public int getScore() {
        return score;
    }
}
