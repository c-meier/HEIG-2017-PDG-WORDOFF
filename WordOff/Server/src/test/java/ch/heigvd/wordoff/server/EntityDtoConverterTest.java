package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dto.Game.*;
import ch.heigvd.wordoff.common.Dto.Game.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.Mode.DuelModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.common.Dto.Mode.TournamentModeDto;
import ch.heigvd.wordoff.common.Dto.User.*;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
public class EntityDtoConverterTest {

    private MockModel model = new MockModel();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TileToDto() throws Exception {
        Tile entity = model.getTile();
        TileDto dto = DtoFactory.createFrom(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getValue(), dto.getValue());
        assertEquals(entity.getScore(), dto.getScore());
    }

    @Test
    public void SlotToDto() throws Exception {
        Slot entity = model.getEmptySlot();
        SlotDto dto = DtoFactory.createFrom(entity);

        assertEquals(entity.getPos(), dto.getPos());
        assertEquals(entity.getTile(), dto.getTile());
    }

    @Test
    public void SlotWithTileToDto() throws Exception {
        Slot entity = model.getSlot();
        SlotDto dto = DtoFactory.createFrom(entity);

        assertEquals(entity.getPos(), dto.getPos());
        assertEquals(TileDto.class, dto.getTile().getClass());
        assertEquals(entity.getTile().getId(), dto.getTile().getId());
        assertEquals(entity.getTile().getValue(), dto.getTile().getValue());
        assertEquals(entity.getTile().getScore(), dto.getTile().getScore());
    }

    @Test
    public void SwapRackToDto() throws Exception {
        SwapRack entity = new SwapRack();

        RackDto dto = DtoFactory.createFrom(entity);

        assertEquals(SwapRackDto.class, dto.getClass());
    }

    @Test
    public void SwapRackWithTilesToDto() throws Exception {
        SwapRack entity = new SwapRack();
        entity.addTile(model.getTile());

        RackDto dto = DtoFactory.createFrom(entity);

        assertEquals(SwapRackDto.class, dto.getClass());
        assertEquals(TileDto.class, dto.getTiles().get(0).getClass());
    }

    @Test
    public void ChallengeToDto() throws Exception {
        Challenge entity = model.getChallenge();
        ChallengeDto dto = DtoFactory.createFrom(entity);

        assertEquals(TileDto.class, dto.getSwapRack().getTiles().get(0).getClass());
        assertEquals(TileDto.class, dto.getSlots().get(0).getTile().getClass());
    }

    @Test
    public void UserToSummaryDto() throws Exception {
        User entity = model.getUserOne();
        UserSummaryDto dto = DtoFactory.createSummaryFrom(entity);
        assertEquals(UserSummaryDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void AiAsPlayerToDto() throws Exception {
        Player entity = model.getAi();
        PlayerDto dto = DtoFactory.createFrom(entity);
        assertEquals(PlayerDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void UserAsPlayerToDto() throws Exception {
        Player entity = model.getUserOne();
        PlayerDto dto = DtoFactory.createFrom(entity);
        assertEquals(UserSummaryDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertNotNull(((UserSummaryDto)dto).getEndpoint());
    }
    
    @Test
    public void UserToMeDto() throws Exception {
        User entity = model.getUserOne();
        MeDto dto = DtoFactory.createMeFrom(entity);
        assertEquals(MeDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getSelf().getId());
        assertEquals(entity.getName(), dto.getSelf().getName());
    }

    @Test
    public void RelationToDto() throws Exception {
        Relation entity = model.getUserTwo().getRelation(model.getUserOne());

        RelationDto dto = DtoFactory.createFrom(entity);
        assertEquals(RelationDto.class, dto.getClass());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals("/me/relations/" + entity.getTarget().getId(), dto.getEndpoint());
    }

    @Test
    public void UserToRelatedSummaryDto() throws Exception {
        User entity = model.getUserOne();
        User viewer = model.getUserTwo();
        RelatedUserSummaryDto dto = DtoFactory.createRelatedSummaryFrom(viewer.getRelation(entity));
        assertEquals(RelatedUserSummaryDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());

        assertEquals(viewer.getRelation(entity).getStatus(), dto.getRelation().getStatus());
    }

    @Test
    public void UserToDto() throws Exception {
        User entity = model.getUserOne();
        UserDto dto = DtoFactory.createFrom(entity);
        assertEquals(UserDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getLevel(), dto.getLevel());
    }

    @Test
    public void UserSideToDto() throws Exception {
        Side entity = new Side(model.getUserOne());

        entity.setChallenge(model.getChallenge());
        entity.setScore(234);
        entity.getPlayerRack().addTile(model.getTile());

        SideDto dto = DtoFactory.createFrom(entity);

        assertEquals(entity.getScore(), dto.getScore());
        assertEquals(UserSummaryDto.class, dto.getPlayer().getClass());
        assertEquals(TileDto.class, dto.getPlayerRack().getTiles().get(0).getClass());
    }

    @Test
    public void AiSideToDto() throws Exception {
        Side entity = new Side(model.getAi());

        SideDto dto = DtoFactory.createFrom(entity);

        assertEquals(PlayerDto.class, dto.getPlayer().getClass());
    }

    @Test
    public void SideToOtherDto() throws Exception {
        Side entity = new Side(model.getUserTwo());

        OtherSideDto dto = DtoFactory.createOtherFrom(entity);

        assertEquals(OtherSideDto.class, dto.getClass());
    }

    @Test
    public void GameToSummaryDto() throws Exception {
        Game entity = model.getDuelGame();

        GameSummaryDto dto = DtoFactory.createSummaryFrom(entity, (User)entity.getCurrPlayer());

        assertEquals(GameSummaryDto.class, dto.getClass());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getOtherPlayer(entity.getCurrPlayer()).getId(), dto.getOtherPlayer().getId());
    }

    @Test
    public void GameToDto() throws Exception {
        Game entity = model.getDuelGame();

        GameDto dto = DtoFactory.createFrom(entity, (User)entity.getCurrPlayer());

        assertEquals(GameDto.class, dto.getClass());
        assertEquals(entity.getBag().getTiles().size(), dto.getBagSize());
    }

    @Test
    public void TournamentModeToDto() {
        TournamentMode entity = model.getTournamentMode();

        TournamentModeDto dto = (TournamentModeDto) DtoFactory.createFrom(entity, model.getUserOne());

        assertEquals(TournamentModeDto.class, dto.getClass());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getInvitation(model.getUserTwo()).getName(), dto.getName());
        assertEquals(entity.getInvitations().size(), dto.getParticipants().size());
    }

    @Test
    public void DuelModeToDto() {
        DuelMode entity = model.getDuelMode();

        ModeDto dto = DtoFactory.createFrom(entity, model.getUserTwo());

        assertEquals(DuelModeDto.class, dto.getClass());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getInvitation(model.getUserTwo()).getName(), dto.getName());
    }

    @Test
    public void TournamentModeToSummaryDto() {
        TournamentMode entity = model.getTournamentMode();

        ModeSummaryDto dto = DtoFactory.createSummaryFrom(entity, model.getUserOne());

        assertEquals(ModeSummaryDto.class, dto.getClass());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(true, dto.isActive());
        assertEquals(entity.getInvitation(model.getUserTwo()).getName(), dto.getName());
    }

    @Test
    public void InvitationToDto() throws Exception {
        Invitation entity = model.getInvitation();

        InvitationDto dto = DtoFactory.createFrom(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals("/me/invitations/" + entity.getMode().getId(), dto.getEndpoint());
    }

    @Test
    public void TileToDao() throws Exception {
        TileDto dto = DtoFactory.createFrom(model.getTile());

        Tile entity = EntityFactory.createFrom(dto);

        assertEquals(Tile.class, entity.getClass());
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getScore(), entity.getScore());
        assertEquals(dto.getValue(), entity.getValue());
    }

    @Test
    public void SlotToDao() throws Exception {
        Slot origin = model.getSlot();
        SlotDto dto = DtoFactory.createFrom(origin);

        Slot entity = EntityFactory.createFrom(dto);

        assertEquals(origin.getClass(), entity.getClass());
    }

    @Test
    public void RackToDao() throws Exception {
        Rack origin = new SwapRack();
        origin.addTile(model.getTile());
        RackDto dto = DtoFactory.createFrom(origin);

        Rack entity = EntityFactory.createFrom(dto);

        assertEquals(origin.getClass(), entity.getClass());
        assertEquals(Tile.class, entity.getTiles().get(0).getClass());
    }

    @Test
    public void ChallengeToDao() throws Exception {
        Challenge origin = model.getChallenge();
        ChallengeDto dto = DtoFactory.createFrom(origin);

        Challenge entity = EntityFactory.createFrom(dto);

        assertEquals(Challenge.class, entity.getClass());
        assertEquals(origin.getSlots().get(0).getClass(), entity.getSlots().get(0).getClass());
    }
}