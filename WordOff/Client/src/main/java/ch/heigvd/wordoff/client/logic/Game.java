package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Model.ChallengeDto;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Model.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Model.Slots.*;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;
import ch.heigvd.wordoff.common.Model.SideDto;
import java.util.Arrays;
import java.util.LinkedList;

public class Game {
    private SideDto sideP1;
    private SideDto sideP2;

    public Game() {

        this.sideP1 = new SideDto();
        this.sideP2 = new SideDto();
        PlayerRackDto pRack = new PlayerRackDto();
        SwapRackDto pSp = new SwapRackDto();
        pRack.setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new TileDto(0, 'a', 1),
                (ITile) new TileDto(1, 'b', 3),
                (ITile) new TileDto(2, 'c', 3),
                (ITile) new TileDto(3, 'd', 2),
                (ITile) new TileDto(4, 'e', 2),
                (ITile) new TileDto(5, 'f', 4),
                (ITile) new TileDto(6, 'g', 2))
        ));
        ChallengeDto pChallenge = new ChallengeDto(Arrays.asList(
                (ISlot) new SlotDto(),
                (ISlot) new SwapSlotDto(),
                (ISlot) new SlotDto(),
                (ISlot) new L2SlotDto(),
                (ISlot) new SwapSlotDto(),
                (ISlot) new SlotDto(),
                (ISlot) new LastSlotDto()
        ));

        pSp.setTiles( new LinkedList<ITile>(Arrays.asList(
                (ITile) new TileDto(7, 'h', 4),
                (ITile)new TileDto(8, 'i', 1))));
        pChallenge.setSwapRack(pSp);

        this.sideP1.setPlayerRack(pRack);
        this.sideP1.setChallenge(pChallenge);
        // Tile int id, char value, int score

        PlayerRackDto p2Rack = new PlayerRackDto();
        p2Rack.setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new TileDto(10, 'j', 8),
                (ITile) new TileDto(11, 'k', 10),
                (ITile) new TileDto(12, 'l', 1),
                (ITile) new TileDto(13, 'm', 2),
                (ITile) new TileDto(14, 'n', 1),
                (ITile) new TileDto(15, 'o', 1),
                (ITile) new TileDto(16, 'p', 3))));
        SwapRackDto p2sp = new SwapRackDto();
        p2sp.setTiles(new LinkedList<ITile>(Arrays.asList(
                (ITile) new TileDto(17, 'q', 8),
                (ITile) new TileDto(18, 'r', 1))));
        ChallengeDto p2Challenge = new ChallengeDto(Arrays.asList(
                (ISlot) new SlotDto(),
                (ISlot) new SwapSlotDto(),
                (ISlot) new SlotDto(),
                (ISlot) new L3SlotDto(),
                (ISlot) new SwapSlotDto(),
                (ISlot) new SlotDto(),
                (ISlot) new LastSlotDto()));
        this.sideP2.setPlayerRack(p2Rack);
        p2Challenge.setSwapRack(p2sp);
        this.sideP2.setChallenge(p2Challenge);
    }

    public SideDto getSideP1() {
        return sideP1;
    }

    public SideDto getSideP2() {
        return sideP2;
    }
}
