package ch.heigvd.wordoff.client.Logic;

import ch.heigvd.wordoff.common.Dto.*;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Slots.L2SlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.LastSlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.SwapSlotDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private SideDto sideP1;
    private SideDto sideP2;

    private GameDto gameDto;

    private List<GameSummaryDto> gameSummaryDtoList = new LinkedList<>();

    public Game() {
        this.sideP1 = new SideDto(1L,
                new PlayerDto(1L, "test1"),
                new ChallengeDto(Arrays.asList(
                    (ISlot) new SlotDto(new Integer(1).shortValue()),
                    (ISlot) new SwapSlotDto(new Integer(2).shortValue()),
                    (ISlot) new SlotDto(new Integer(3).shortValue()),
                    (ISlot) new L2SlotDto(new Integer(4).shortValue()),
                    (ISlot) new SwapSlotDto(new Integer(5).shortValue()),
                    (ISlot) new SlotDto(new Integer(6).shortValue()),
                    (ISlot) new LastSlotDto(new Integer(7).shortValue())),
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
                    (ISlot) new SlotDto(new Integer(1).shortValue()),
                    (ISlot) new SwapSlotDto(new Integer(2).shortValue()),
                    (ISlot) new SlotDto(new Integer(3).shortValue()),
                    (ISlot) new L2SlotDto(new Integer(4).shortValue()),
                    (ISlot) new SwapSlotDto(new Integer(5).shortValue()),
                    (ISlot) new SlotDto(new Integer(6).shortValue()),
                    (ISlot) new LastSlotDto(new Integer(7).shortValue())),
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

        long id = 46465464;
        OtherSideDto otherSideDto = new OtherSideDto(id,sideP2.getPlayer(),sideP2.getChallenge(),0);
        this.gameDto = new GameDto(id, sideP1, otherSideDto,true, "fr", null);

        this.gameSummaryDtoList.add(new GameSummaryDto(id,  new PlayerDto(1L, "Game Test Static")));
    }

    public GameDto getGameDto() {
        return gameDto;
    }

    public List<GameSummaryDto> getGameSummaryDtoList() {
        return gameSummaryDtoList;
    }

    public Game (GameDto game){
        this.sideP1 = new SideDto(1L,
                new PlayerDto(1L, "test1"),
                new ChallengeDto(Arrays.asList(
                (ISlot) new SlotDto(new Integer(1).shortValue()),
                (ISlot) new SwapSlotDto(new Integer(2).shortValue()),
                (ISlot) new SlotDto(new Integer(3).shortValue()),
                (ISlot) new L2SlotDto(new Integer(4).shortValue()),
                (ISlot) new SwapSlotDto(new Integer(5).shortValue()),
                (ISlot) new SlotDto(new Integer(6).shortValue()),
                (ISlot) new LastSlotDto(new Integer(7).shortValue())),
                        new SwapRackDto(new LinkedList<ITile>(Arrays.asList(
                                (ITile) new TileDto(7, 'h', 4),
                                (ITile)new TileDto(8, 'i', 1))))),
                new PlayerRackDto(new LinkedList<ITile>(Arrays.asList(
                        (ITile) new TileDto(4, 'z', 2),
                        (ITile) new TileDto(5, 'x', 4),
                        (ITile) new TileDto(6, 'y', 2)))),
                0
        );
        this.sideP2 = new SideDto(2L,
                new PlayerDto(2L, "test2"),
                new ChallengeDto(Arrays.asList(
                        (ISlot) new SlotDto(new Integer(1).shortValue()),
                        (ISlot) new SwapSlotDto(new Integer(2).shortValue()),
                        (ISlot) new SlotDto(new Integer(3).shortValue()),
                        (ISlot) new L2SlotDto(new Integer(4).shortValue()),
                        (ISlot) new SwapSlotDto(new Integer(5).shortValue()),
                        (ISlot) new SlotDto(new Integer(6).shortValue()),
                        (ISlot) new LastSlotDto(new Integer(7).shortValue())),
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

        long id = 46465464;
        OtherSideDto otherSideDto = new OtherSideDto(id,sideP2.getPlayer(),sideP2.getChallenge(),0);
        this.gameDto = new GameDto(id, sideP1, otherSideDto,true, "fr", null);
        this.gameDto.setMyTurn(false);
        this.gameSummaryDtoList.add(new GameSummaryDto(id,  new PlayerDto(1L, "Game Test Static")));
    }
}
