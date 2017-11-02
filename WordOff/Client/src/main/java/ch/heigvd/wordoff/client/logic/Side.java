package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.Model.ChallengeDto;
import ch.heigvd.wordoff.common.Model.PlayerDto;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Model.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Model.Slots.L2SlotDto;
import ch.heigvd.wordoff.common.Model.Slots.LastSlotDto;
import ch.heigvd.wordoff.common.Model.Slots.SlotDto;
import ch.heigvd.wordoff.common.Model.Slots.SwapSlotDto;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;
import javafx.beans.property.BooleanProperty;

import java.util.Arrays;
import java.util.List;

public class Side {
    private PlayerDto player;
    private ChallengeDto challenge;
    private SwapRackDto swapRack;
    private PlayerRackDto playerRack;
    private Game game;
    private BooleanProperty playerTurn;


    public Side(){
        this.playerRack = new PlayerRackDto();
        this.swapRack = new SwapRackDto();
        this.challenge = new ChallengeDto(Arrays.asList(
                (ISlot) new SlotDto(),
                (ISlot) new SwapSlotDto(),
                (ISlot) new SlotDto(),
                (ISlot) new L2SlotDto(),
                (ISlot) new SwapSlotDto(),
                (ISlot) new SlotDto(),
                (ISlot) new LastSlotDto()
        ));
    }

    public PlayerDto getPlayer(){
        return player;
    }

    public SwapRackDto getSwapRack() {
        return swapRack;
    }

    public PlayerRackDto getPlayerRack() {
        return playerRack;
    }

    public ChallengeDto getChallenge() {
        return challenge;
    }

    public void majRack(List<TileDto> newTiles, List<TileDto> swapTiles) {
        for (int i = 0; i < newTiles.size(); i++) {
            playerRack.addTile(newTiles.get(i));
        }

        for (int i = 0; i < swapTiles.size(); i++) {
            swapRack.addTile(swapTiles.get(i));
        }
    }

}
