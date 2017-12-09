package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dto.Game.*;
import ch.heigvd.wordoff.common.Dto.Game.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Dto.User.PlayerDto;
import ch.heigvd.wordoff.common.Dto.User.UserDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.Rack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.Slot;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import ch.heigvd.wordoff.server.Util.EntityFactory;
import ch.heigvd.wordoff.server.Utils.MockModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.*;

@ActiveProfiles("test")
public class DaoDtoConverterTest {

    MockModel model = new MockModel();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TileToDto() throws Exception {
        Tile dao = model.getTile();
        TileDto dto = DtoFactory.createFrom(dao);
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getValue(), dto.getValue());
        assertEquals(dao.getScore(), dto.getScore());
    }

    @Test
    public void SlotToDto() throws Exception {
        Slot dao = model.getEmptySlot();
        SlotDto dto = DtoFactory.createFrom(dao);

        assertEquals(dao.getPos(), dto.getPos());
        assertEquals(dao.getTile(), dto.getTile());
    }

    @Test
    public void SlotWithTileToDto() throws Exception {
        Slot dao = model.getSlot();
        SlotDto dto = DtoFactory.createFrom(dao);

        assertEquals(dao.getPos(), dto.getPos());
        assertEquals(TileDto.class, dto.getTile().getClass());
        assertEquals(dao.getTile().getId(), dto.getTile().getId());
        assertEquals(dao.getTile().getValue(), dto.getTile().getValue());
        assertEquals(dao.getTile().getScore(), dto.getTile().getScore());
    }

    @Test
    public void SwapRackToDto() throws Exception {
        SwapRack dao = new SwapRack();

        RackDto dto = DtoFactory.createFrom(dao);

        assertEquals(SwapRackDto.class, dto.getClass());
    }

    @Test
    public void SwapRackWithTilesToDto() throws Exception {
        SwapRack dao = new SwapRack();
        dao.addTile(model.getTile());

        RackDto dto = DtoFactory.createFrom(dao);

        assertEquals(SwapRackDto.class, dto.getClass());
        assertEquals(TileDto.class, dto.getTiles().get(0).getClass());
    }

    @Test
    public void ChallengeToDto() throws Exception {
        Challenge dao = model.getChallenge();
        ChallengeDto dto = DtoFactory.createFrom(dao);

        assertEquals(TileDto.class, dto.getSwapRack().getTiles().get(0).getClass());
        assertEquals(TileDto.class, dto.getSlots().get(0).getTile().getClass());
    }

    @Test
    public void UserToSummaryDto() throws Exception {
        User dao = model.getUserOne();
        UserSummaryDto dto = DtoFactory.createSummaryFrom(dao);
        assertEquals(UserSummaryDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
    }

    @Test
    public void AiAsPlayerToDto() throws Exception {
        Player dao = model.getAi();
        PlayerDto dto = DtoFactory.createFrom(dao);
        assertEquals(PlayerDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
    }

    @Test
    public void UserAsPlayerToDto() throws Exception {
        Player dao = model.getUserOne();
        PlayerDto dto = DtoFactory.createFrom(dao);
        assertEquals(UserSummaryDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
        assertNotNull(((UserSummaryDto)dto).getEndpoint());
    }

    @Test
    public void UserToDto() throws Exception {
        User dao = model.getUserOne();
        UserDto dto = DtoFactory.createFrom(dao);
        assertEquals(UserDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getName(), dto.getName());
        assertEquals(dao.getLevel(), dto.getLevel());
    }

    @Test
    public void UserSideToDto() throws Exception {
        Side dao = new Side(model.getUserOne());

        dao.setChallenge(model.getChallenge());
        dao.setScore(234);
        dao.getPlayerRack().addTile(model.getTile());

        SideDto dto = DtoFactory.createFrom(dao);

        assertEquals(dao.getScore(), dto.getScore());
        assertEquals(UserSummaryDto.class, dto.getPlayer().getClass());
        assertEquals(TileDto.class, dto.getPlayerRack().getTiles().get(0).getClass());
    }

    @Test
    public void AiSideToDto() throws Exception {
        Side dao = new Side(model.getAi());

        SideDto dto = DtoFactory.createFrom(dao);

        assertEquals(PlayerDto.class, dto.getPlayer().getClass());
    }

    @Test
    public void SideToOtherDto() throws Exception {
        Side dao = new Side(model.getUserTwo());

        OtherSideDto dto = DtoFactory.createOtherFrom(dao);

        assertEquals(OtherSideDto.class, dto.getClass());
    }

    @Test
    public void GameToSummaryDto() throws Exception {
        Game dao = model.getDuelGame();

        GameSummaryDto dto = DtoFactory.createSummaryFrom(dao, dao.getCurrPlayer());

        assertEquals(GameSummaryDto.class, dto.getClass());
        assertEquals(dao.getId(), dto.getId());
        assertEquals(dao.getOtherPlayer(dao.getCurrPlayer()).getId(), dto.getOtherPlayer().getId());
    }

    @Test
    public void GameToDto() throws Exception {
        Game dao = model.getDuelGame();

        GameDto dto = DtoFactory.createFrom(dao, dao.getCurrPlayer());

        assertEquals(GameDto.class, dto.getClass());
        assertEquals(dao.getBag().getTiles().size(), dto.getBagSize());
    }

    @Test
    public void TileToDao() throws Exception {
        TileDto dto = DtoFactory.createFrom(model.getTile());

        Tile dao = EntityFactory.createFrom(dto);

        assertEquals(Tile.class, dao.getClass());
        assertEquals(dto.getId(), dao.getId());
        assertEquals(dto.getScore(), dao.getScore());
        assertEquals(dto.getValue(), dao.getValue());
    }

    @Test
    public void SlotToDao() throws Exception {
        Slot origin = model.getSlot();
        SlotDto dto = DtoFactory.createFrom(origin);

        Slot dao = EntityFactory.createFrom(dto);

        assertEquals(origin.getClass(), dao.getClass());
    }

    @Test
    public void RackToDao() throws Exception {
        Rack origin = new SwapRack();
        origin.addTile(model.getTile());
        RackDto dto = DtoFactory.createFrom(origin);

        Rack dao = EntityFactory.createFrom(dto);

        assertEquals(origin.getClass(), dao.getClass());
        assertEquals(Tile.class, dao.getTiles().get(0).getClass());
    }

    @Test
    public void ChallengeToDao() throws Exception {
        Challenge origin = model.getChallenge();
        ChallengeDto dto = DtoFactory.createFrom(origin);

        Challenge dao = EntityFactory.createFrom(dto);

        assertEquals(Challenge.class, dao.getClass());
        assertEquals(origin.getSlots().get(0).getClass(), dao.getSlots().get(0).getClass());
    }
}