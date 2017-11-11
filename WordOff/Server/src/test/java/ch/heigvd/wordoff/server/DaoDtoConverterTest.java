package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dto.*;
import ch.heigvd.wordoff.common.Dto.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.L2Slot;
import ch.heigvd.wordoff.server.Model.Slots.LastSlot;
import ch.heigvd.wordoff.server.Model.Slots.Slot;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Util.DaoDtoConverter;
import ch.heigvd.wordoff.server.Utils.MockModel;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.Assert.*;

@ActiveProfiles("test")
public class DaoDtoConverterTest {
    DaoDtoConverter converter;

    MockModel model = new MockModel();

    @Before
    public void setUp() throws Exception {
        converter = new DaoDtoConverter();
    }

    @Test
    public void TileToDto() throws Exception {
        Tile dao = model.getTile();
        TileDto dto = converter.toDto(dao);
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getValue(), dto.getValue());
        assertEquals(dao.getScore(), dto.getScore());
    }

    @Test
    public void SlotToDto() throws Exception {
        Slot dao = new Slot((short)1);
        SlotDto dto = converter.toDto(dao);

        assertEquals(dao.getPos(), dto.getPos());
        assertEquals(dao.getTile(), dto.getTile());
    }

    @Test
    public void SlotWithTileToDto() throws Exception {
        Slot dao = new Slot((short)1);
        dao.setTile(model.getTile());
        SlotDto dto = converter.toDto(dao);

        assertEquals(dao.getPos(), dto.getPos());
        assertEquals(TileDto.class, dto.getTile().getClass());
        assertEquals(dao.getTile().getId(), dto.getTile().getId());
        assertEquals(dao.getTile().getValue(), dto.getTile().getValue());
        assertEquals(dao.getTile().getScore(), dto.getTile().getScore());
    }

    @Test
    public void SwapRackToDto() throws Exception {
        SwapRack dao = new SwapRack();
        RackDto dto = converter.toDto(dao);
    }

    @Test
    public void SwapRackWithTilesToDto() throws Exception {
        SwapRack dao = new SwapRack();
        RackDto dto = converter.toDto(dao);
    }

    @Test
    public void ChallengeToDto() throws Exception {
        Slot slot1 = new Slot((short)1);
        slot1.setTile(model.getTile());

        Slot slot2 = new L2Slot((short)2);
        slot2.setTile(model.getTile());

        Challenge dao = new Challenge(Arrays.asList(
            slot1, slot2, new LastSlot((short)3)
        ));
        dao.getSwapRack().addTile(model.getTile());
        ChallengeDto dto = converter.toDto(dao);

        assertEquals(TileDto.class, dto.getSwapRack().getTiles().get(0).getClass());
        assertEquals(TileDto.class, dto.getSlots().get(0).getTile().getClass());
    }

    @Test
    public void UserToSummaryDto() throws Exception {
        User dao = model.getUserOne();
        UserSummaryDto dto = converter.toSummaryDto(dao);
        assertEquals(UserSummaryDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
    }

    @Test
    public void AiAsPlayerToDto() throws Exception {
        Player dao = model.getAi();
        PlayerDto dto = converter.toDto(dao);
        assertEquals(PlayerDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
    }

    @Test
    public void UserAsPlayerToDto() throws Exception {
        Player dao = model.getUserOne();
        PlayerDto dto = converter.toDto(dao);
        assertEquals(UserSummaryDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
        assertNotNull(((UserSummaryDto)dto).getEndpoint());
    }

    @Test
    public void UserToDto() throws Exception {
        User dao = model.getUserOne();
        UserDto dto = converter.toDto(dao);
        assertEquals(UserDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
        assertEquals(dao.getLevel(), dto.getLevel());
    }

    @Test
    public void UserSideToDto() throws Exception {
        Side dao = new Side(model.getUserOne());

        Slot slot1 = new Slot((short)1);
        slot1.setTile(model.getTile());

        Slot slot2 = new L2Slot((short)2);
        slot2.setTile(model.getTile());

        Challenge challenge = new Challenge(Arrays.asList(
                slot1, slot2, new LastSlot((short)3)
        ));
        challenge.getSwapRack().addTile(model.getTile());

        dao.setChallenge(challenge);
        dao.setScore(234);
        dao.getPlayerRack().addTile(model.getTile());

        SideDto dto = converter.toDto(dao);

        assertEquals(dao.getScore(), dto.getScore());
        assertEquals(UserSummaryDto.class, dto.getPlayer().getClass());
        assertEquals(TileDto.class, dto.getPlayerRack().getTiles().get(0).getClass());
    }

    @Test
    public void AiSideToDto() throws Exception {
        Side dao = new Side(model.getAi());

        SideDto dto = converter.toDto(dao);

        assertEquals(PlayerDto.class, dto.getPlayer().getClass());
    }

    @Test
    public void SideToOtherDto() throws Exception {
        Side dao = new Side(model.getUserTwo());

        OtherSideDto dto = converter.toOtherDto(dao);

        assertEquals(OtherSideDto.class, dto.getClass());
    }

    @Test
    public void GameToSummaryDto() throws Exception {
        Game dao = model.getDuelGame();

        GameSummaryDto dto = converter.toSummaryDto(dao, dao.getCurrPlayer());

        assertEquals(GameSummaryDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getOtherPlayer(dao.getCurrPlayer()).getId(), dto.getOtherPlayer().getId());
    }

    @Test
    public void GameToDto() throws Exception {
        Game dao = model.getDuelGame();

        GameDto dto = converter.toDto(dao, dao.getCurrPlayer());

        assertEquals(GameDto.class, dto.getClass());
    }
}