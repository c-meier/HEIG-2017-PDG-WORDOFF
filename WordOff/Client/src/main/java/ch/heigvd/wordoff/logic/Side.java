package ch.heigvd.wordoff.logic;

import ch.heigvd.wordoff.logic.Racks.PlayerRack;
import ch.heigvd.wordoff.logic.Racks.SwapRack;
import javafx.beans.property.BooleanProperty;

public class Side {
    private Player player;
    private Challenge challenge;
    private SwapRack swapRack;
    private PlayerRack playerRack;
    private Game game;
    private BooleanProperty playerTurn;
}
