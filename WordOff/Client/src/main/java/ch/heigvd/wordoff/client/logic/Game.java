package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.Dto.*;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Slots.*;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class Game {
    private SideDto sideP1;
    private SideDto sideP2;

    private GameDto gameDto;

    public Game() {



        this.sideP1 = new SideDto(1L,
                new PlayerDto(1L, "test1"),
                new ChallengeDto(Arrays.asList(
                    (ISlot) new SlotDto(1L, new Integer(1).shortValue()),
                    (ISlot) new SwapSlotDto(1L, new Integer(2).shortValue()),
                    (ISlot) new SlotDto(1L, new Integer(3).shortValue()),
                    (ISlot) new L2SlotDto(1L, new Integer(4).shortValue()),
                    (ISlot) new SwapSlotDto(1L, new Integer(5).shortValue()),
                    (ISlot) new SlotDto(1L, new Integer(6).shortValue()),
                    (ISlot) new LastSlotDto(1L, new Integer(7).shortValue())),
                new SwapRackDto(new LinkedList<ITile>(Arrays.asList(
                    (ITile) new TileDto(7, 'h', 4),
                    (ITile)new TileDto(8, 'i', 1))))),
                new PlayerRackDto(new LinkedList<ITile>(Arrays.asList(
                    (ITile) new TileDto(0, 'a', 1),
                    (ITile) new TileDto(1, 'b', 3),
                    (ITile) new TileDto(2, 'c', 3),
                    (ITile) new TileDto(3, 'd', 2),
                    (ITile) new TileDto(4, 'e', 2),
                    (ITile) new TileDto(5, 'f', 4),
                    (ITile) new TileDto(6, 'g', 2)))),
                0
        );


        this.sideP2 = new SideDto(2L,
                new PlayerDto(2L, "test2"),
                new ChallengeDto(Arrays.asList(
                    (ISlot) new SlotDto(2L, new Integer(1).shortValue()),
                    (ISlot) new SwapSlotDto(2L, new Integer(2).shortValue()),
                    (ISlot) new SlotDto(2L, new Integer(3).shortValue()),
                    (ISlot) new L2SlotDto(2L, new Integer(4).shortValue()),
                    (ISlot) new SwapSlotDto(2L, new Integer(5).shortValue()),
                    (ISlot) new SlotDto(2L, new Integer(6).shortValue()),
                    (ISlot) new LastSlotDto(2L, new Integer(7).shortValue())),
                new SwapRackDto(new LinkedList<ITile>(Arrays.asList(
                    (ITile) new TileDto(17, 'q', 8),
                    (ITile) new TileDto(18, 'r', 1))))),
                new PlayerRackDto(new LinkedList<ITile>(Arrays.asList(
                    (ITile) new TileDto(10, 'j', 8),
                    (ITile) new TileDto(11, 'k', 10),
                    (ITile) new TileDto(12, 'l', 1),
                    (ITile) new TileDto(13, 'm', 2),
                    (ITile) new TileDto(14, 'n', 1),
                    (ITile) new TileDto(15, 'o', 1),
                    (ITile) new TileDto(16, 'p', 3)))),
                0
        );

        long id =46465464;
        OtherSideDto otherSideDto = new OtherSideDto(id,sideP2.getPlayer(),sideP2.getChallenge(),0);
        this.gameDto = new GameDto(id, sideP1, otherSideDto,true, "fr", null);
    }

    public GameDto getGameDto() {
        return gameDto;
    }
}
