package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dto.Game.*;
import ch.heigvd.wordoff.common.Dto.Game.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.*;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.Mode.DuelModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.common.Dto.Mode.TournamentModeDto;
import ch.heigvd.wordoff.common.Dto.NotificationDto;
import ch.heigvd.wordoff.common.Dto.User.*;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.Rack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.*;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DtoFactory {
    private static ModelMapper modelMapper = configuredModelMapper();

    private static ModelMapper configuredModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Tile, ITile> toTileDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), TileDto.class);
        Converter<Slot, ISlot> toSlotDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), SlotDto.class);
        Converter<Rack, IRack> toRackDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), RackDto.class);
        Converter<Player, PlayerDto> toPlayerDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), PlayerDto.class);

        //
        // Tiles
        //
        TypeMap<Tile, ITile> tileMap = modelMapper.createTypeMap(Tile.class, ITile.class);
        tileMap.setProvider(req -> new TileDto());

        tileMap.include(Tile.class, TileDto.class);


        //
        // Racks
        //
        TypeMap<Rack, IRack> rackMap = modelMapper.createTypeMap(Rack.class, IRack.class)
                .include(SwapRack.class, IRack.class)
                .include(PlayerRack.class, IRack.class)
                .include(SwapRack.class, RackDto.class)
                .include(PlayerRack.class, RackDto.class);
        modelMapper.typeMap(SwapRack.class, IRack.class).setProvider(req -> new SwapRackDto());
        modelMapper.typeMap(PlayerRack.class, IRack.class).setProvider(req -> new PlayerRackDto());
        modelMapper.typeMap(SwapRack.class, RackDto.class).setProvider(req -> new SwapRackDto());
        modelMapper.typeMap(PlayerRack.class, RackDto.class).setProvider(req -> new PlayerRackDto());

        //
        // Slots
        //
        TypeMap<Slot, ISlot> slotMap = modelMapper.createTypeMap(Slot.class, ISlot.class);
        slotMap.addMappings(mapper -> mapper.using(toTileDto).map(Slot::getTile, ISlot::setTile));

        slotMap.include(SwapSlot.class, ISlot.class)
                .include(L2Slot.class, ISlot.class)
                .include(L3Slot.class, ISlot.class)
                .include(LastSlot.class, ISlot.class)
                .include(Slot.class, SlotDto.class)
                .include(SwapSlot.class, SlotDto.class)
                .include(L2Slot.class, SlotDto.class)
                .include(L3Slot.class, SlotDto.class)
                .include(LastSlot.class, SlotDto.class);

        modelMapper.typeMap(SwapSlot.class, SlotDto.class).setProvider(req -> new SwapSlotDto());
        modelMapper.typeMap(L2Slot.class, SlotDto.class).setProvider(req -> new L2SlotDto());
        modelMapper.typeMap(L3Slot.class, SlotDto.class).setProvider(req -> new L3SlotDto());
        modelMapper.typeMap(LastSlot.class, SlotDto.class).setProvider(req -> new LastSlotDto());
        modelMapper.typeMap(Slot.class, ISlot.class).setProvider(req -> new SlotDto());
        modelMapper.typeMap(SwapSlot.class, ISlot.class).setProvider(req -> new SwapSlotDto());
        modelMapper.typeMap(L2Slot.class, ISlot.class).setProvider(req -> new L2SlotDto());
        modelMapper.typeMap(L3Slot.class, ISlot.class).setProvider(req -> new L3SlotDto());
        modelMapper.typeMap(LastSlot.class, ISlot.class).setProvider(req -> new LastSlotDto());

        //
        // Players
        //
        TypeMap<Player, PlayerDto> playerMap = modelMapper.createTypeMap(Player.class, PlayerDto.class)
                .include(User.class, PlayerDto.class)
                .include(Ai.class, PlayerDto.class);

        modelMapper.typeMap(User.class, PlayerDto.class).setProvider(req -> new UserSummaryDto());

        //
        // Sides
        //
        TypeMap<Side, OtherSideDto> sideMap = modelMapper.createTypeMap(Side.class, OtherSideDto.class);
        sideMap.addMappings(mapper -> mapper.using(toPlayerDto).map(Side::getPlayer, OtherSideDto::setPlayer));
        sideMap.include(Side.class, SideDto.class);

        modelMapper.typeMap(Side.class, SideDto.class).addMapping(Side::getPlayerRack, SideDto::setPlayerRack);

        //
        // Games
        //
        Converter<Bag, Integer> bagConverter = ctx -> ctx.getSource().getTiles().size();

        TypeMap<Game, GameDto> gameMap = modelMapper.createTypeMap(Game.class, GameDto.class);
        gameMap.addMappings(mapper -> {
            mapper.using(bagConverter).map(Game::getBag, GameDto::setBagSize);
            mapper.using(getUrlConverter("/games")).map(Game::getId, GameDto::setEndpoint);
        });

        TypeMap<Game, GameSummaryDto> gameSummaryMap = modelMapper.createTypeMap(Game.class, GameSummaryDto.class);
        gameSummaryMap.addMappings(mapper -> {
            mapper.using(getUrlConverter("/games")).map(Game::getId, GameSummaryDto::setEndpoint);
        });

        //
        // Relations
        //
        TypeMap<Relation, RelationDto> relationMap = modelMapper.createTypeMap(Relation.class, RelationDto.class);
        relationMap.addMappings(mapper -> mapper
                .using(getUrlConverter("/me/relations"))
                .map(r -> r.getTarget().getId(), RelationDto::setEndpoint));

        //
        // Invitations
        //
        TypeMap<Invitation, InvitationDto> invitationMap = modelMapper.createTypeMap(Invitation.class, InvitationDto.class);
        invitationMap.addMappings(mapper -> mapper
                .using(getUrlConverter("/me/invitations"))
                .map(i -> i.getMode().getId(), InvitationDto::setEndpoint));

        //
        // Mode
        //
        TypeMap<Mode, ModeSummaryDto> modeSummaryMap = modelMapper.createTypeMap(Mode.class, ModeSummaryDto.class);
        modeSummaryMap.addMappings(mapper -> mapper.using(getUrlConverter("/modes")).map(Mode::getId, ModeSummaryDto::setEndpoint));
        modeSummaryMap.include(TournamentMode.class, ModeSummaryDto.class)
                .include(DuelMode.class, ModeSummaryDto.class);

        TypeMap<Mode, ModeDto> modeMap = modelMapper.createTypeMap(Mode.class, ModeDto.class);
        modeMap.addMappings(mapper -> mapper.using(getUrlConverter("/modes")).map(Mode::getId, ModeDto::setEndpoint));
        modeMap.include(TournamentMode.class, TournamentModeDto.class)
                .include(DuelMode.class, DuelModeDto.class);

        Converter<Map<User, List<Integer>>, List<TournamentModeDto.UserScores>> toUsersScores =
                ctx -> ctx.getSource().entrySet()
                        .stream()
                        .map(e -> new TournamentModeDto.UserScores(DtoFactory.createSummaryFrom(e.getKey()), e.getValue()))
                        .collect(Collectors.toList());

        modelMapper.typeMap(TournamentMode.class, TournamentModeDto.class)
                .setProvider(req -> new TournamentModeDto())
                .addMappings(mapper -> mapper
                        .using(toUsersScores)
                        .map(TournamentMode::getAllPlayerScores, TournamentModeDto::setParticipants));
        modelMapper.typeMap(DuelMode.class, DuelModeDto.class).setProvider(req -> new DuelModeDto());


        return modelMapper;
    }

    private static Converter<Long, String> getUrlConverter(String resource) {
        return ctx -> resource + "/" + ctx.getSource();
    }

    public static TileDto createFrom(Tile entity) {
        return modelMapper.map(entity, TileDto.class);
    }

    public static SlotDto createFrom(Slot entity) {
        return modelMapper.map(entity, SlotDto.class);
    }

    public static RackDto createFrom(Rack entity) {
        return modelMapper.map(entity, RackDto.class);
    }

    public static ChallengeDto createFrom(Challenge entity) {
        return modelMapper.map(entity, ChallengeDto.class);
    }

    public static UserSummaryDto createSummaryFrom(User entity) {
        return modelMapper.map(entity, UserSummaryDto.class);
    }

    public static UserDto createFrom(User entity, User viewer) {
        UserDto dto = modelMapper.map(entity, UserDto.class);

        dto.setRelation(createFrom(viewer.getRelation(entity)));
        return dto;
    }

    public static PlayerDto createFrom(Player entity) {
        return modelMapper.map(entity, PlayerDto.class);
    }

    public static SideDto createFrom(Side entity) {
        return modelMapper.map(entity, SideDto.class);
    }

    public static OtherSideDto createOtherFrom(Side entity) {
        return modelMapper.map(entity, OtherSideDto.class);
    }

    public static GameDto createFrom(Game entity, User viewer) {
        Side mySide = entity.getSideOfPlayer(viewer);
        Side otherSide = entity.getSideOfPlayer(entity.getOtherPlayer(viewer));

        GameDto dto = modelMapper.map(entity, GameDto.class);
        dto.setMySide(modelMapper.map(
                mySide, SideDto.class));
        dto.setOtherSide(modelMapper.map(
                otherSide, OtherSideDto.class));
        dto.setMyTurn(Objects.equals(entity.getCurrPlayer().getId(), viewer.getId()));

        // If it's the viewer turn, then he sees the last completed challenge (if it exists) of his
        // adversary.
        List<Answer> otherAnswers = otherSide.getAnswers();
        if(dto.isMyTurn() && !otherAnswers.isEmpty()) {
            Challenge lastChallenge = otherAnswers.get(otherAnswers.size() - 1).getChallenge();
            dto.getOtherSide().setChallenge(modelMapper.map(lastChallenge, ChallengeDto.class));
        }

        return dto;
    }

    public static GameSummaryDto createSummaryFrom(Game entity, User viewer) {
        GameSummaryDto dto = modelMapper.map(entity, GameSummaryDto.class);
        dto.setOtherPlayer(modelMapper.map(entity.getOtherPlayer(viewer), PlayerDto.class));
        return dto;
    }

    public static ModeSummaryDto createSummaryFrom(Mode entity, User viewer) {
        ModeSummaryDto dto = modelMapper.map(entity, ModeSummaryDto.class);

        // Name for the viewer.
        dto.setName(entity.getInvitation(viewer).getName());

        // Check if mode is active.
        Optional<Game> optGame = entity.getActiveGame(viewer);
        dto.setActive(optGame.isPresent() && Objects.equals(optGame.get().getCurrPlayer().getId(), viewer.getId()));

        return dto;
    }

    public static ModeDto createFrom(Mode entity, User viewer) {
        ModeDto dto = null;
        switch (entity.getType()) {
            case FRIEND_DUEL:
            case RANDOM_DUEL:
                dto = modelMapper.map(entity, DuelModeDto.class);
                break;
            case COMPETITIVE_TOURNAMENT:
            case FRIENDLY_TOURNAMENT:
                dto = modelMapper.map(entity, TournamentModeDto.class);
                ((TournamentModeDto) dto).setNbGameRemaining(Constants.MAX_GAMES_PER_DAY - ((TournamentMode) entity).getGamesOfCurrentDayAndPlayer(viewer).size());
                break;
        }

        // Name for the viewer.
        dto.setName(entity.getInvitation(viewer).getName());

        Optional<Game> optGame = entity.getActiveGame(viewer);
        if(optGame.isPresent()) {
            dto.setGame(createSummaryFrom(optGame.get(), viewer));
        }
        
        return dto;
    }
    
    public static MeDto createMeFrom(User entity) {
        MeDto dto = new MeDto();
        dto.setSelf(modelMapper.map(entity, UserSummaryDto.class));
        return dto;
    }

    public static RelationDto createFrom(Relation entity) {
        return modelMapper.map(entity, RelationDto.class);
    }

    /**
     * Create a summary of user with the status of the relation from a Relation
     * @param entity The relation from which the summary is created.
     * @return A user summary with relation status.
     */
    public static RelatedUserSummaryDto createRelatedSummaryFrom(Relation entity) {
        RelatedUserSummaryDto dto = modelMapper.map(entity.getTarget(), RelatedUserSummaryDto.class);
        dto.setRelation(createFrom(entity));
        return dto;
    }

    public static InvitationDto createFrom(Invitation entity) {
        InvitationDto dto = modelMapper.map(entity, InvitationDto.class);
        switch (entity.getMode().getType()) {
            case FRIEND_DUEL:
            case RANDOM_DUEL:
                dto.setName("Duel: " + dto.getName());
                break;
            case COMPETITIVE_TOURNAMENT:
            case FRIENDLY_TOURNAMENT:
                dto.setName("Tournoi: " + dto.getName());
                break;
        }
        return dto;
    }

    public static NotificationDto createFrom(Notification entity) {
        return modelMapper.map(entity, NotificationDto.class);
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
