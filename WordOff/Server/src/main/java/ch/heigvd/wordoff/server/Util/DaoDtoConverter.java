package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.Dto.Game.*;
import ch.heigvd.wordoff.common.Dto.Game.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.*;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Dto.User.PlayerDto;
import ch.heigvd.wordoff.common.Dto.User.UserDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.Rack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.*;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import org.modelmapper.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DaoDtoConverter {
    ModelMapper modelMapper;

    public DaoDtoConverter() {
        this.modelMapper = new ModelMapper();

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
        gameMap.addMappings(mapper -> mapper.using(bagConverter).map(Game::getBag, GameDto::setBagSize));

        // ============================
        //  Reverse conversion
        // ============================

        Converter<Tile, ITile> toTileDao =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), Tile.class);

        // Tiles
        //
        TypeMap<TileDto, ITile> tileReverseMap = modelMapper.createTypeMap(TileDto.class, ITile.class);
        tileReverseMap.setProvider(req -> new Tile());

        tileReverseMap.include(TileDto.class, Tile.class);

        //
        // Racks
        //
        TypeMap<RackDto, IRack> rackReverseMap = modelMapper.createTypeMap(RackDto.class, IRack.class)
                .include(SwapRackDto.class, IRack.class)
                .include(PlayerRackDto.class, IRack.class)
                .include(SwapRackDto.class, Rack.class)
                .include(PlayerRackDto.class, Rack.class);
        modelMapper.typeMap(SwapRackDto.class, IRack.class).setProvider(req -> new SwapRack());
        modelMapper.typeMap(PlayerRackDto.class, IRack.class).setProvider(req -> new PlayerRack());
        modelMapper.typeMap(SwapRackDto.class, Rack.class).setProvider(req -> new SwapRack());
        modelMapper.typeMap(PlayerRackDto.class, Rack.class).setProvider(req -> new PlayerRack());

        //
        // Slots
        //
        TypeMap<SlotDto, ISlot> slotReverseMap = modelMapper.createTypeMap(SlotDto.class, ISlot.class);
        slotReverseMap.addMappings(mapper -> mapper.using(toTileDao).map(SlotDto::getTile, ISlot::setTile));

        slotReverseMap.include(SwapSlotDto.class, ISlot.class)
                .include(L2SlotDto.class, ISlot.class)
                .include(L3SlotDto.class, ISlot.class)
                .include(LastSlotDto.class, ISlot.class)
                .include(SlotDto.class, Slot.class)
                .include(SwapSlotDto.class, Slot.class)
                .include(L2SlotDto.class, Slot.class)
                .include(L3SlotDto.class, Slot.class)
                .include(LastSlotDto.class, Slot.class);

        modelMapper.typeMap(SwapSlotDto.class, Slot.class).setProvider(req -> new SwapSlot());
        modelMapper.typeMap(L2SlotDto.class, Slot.class).setProvider(req -> new L2Slot());
        modelMapper.typeMap(L3SlotDto.class, Slot.class).setProvider(req -> new L3Slot());
        modelMapper.typeMap(LastSlotDto.class, Slot.class).setProvider(req -> new LastSlot());
        modelMapper.typeMap(SlotDto.class, ISlot.class).setProvider(req -> new Slot());
        modelMapper.typeMap(SwapSlotDto.class, ISlot.class).setProvider(req -> new SwapSlot());
        modelMapper.typeMap(L2SlotDto.class, ISlot.class).setProvider(req -> new L2Slot());
        modelMapper.typeMap(L3SlotDto.class, ISlot.class).setProvider(req -> new L3Slot());
        modelMapper.typeMap(LastSlotDto.class, ISlot.class).setProvider(req -> new LastSlot());

    }

    public TileDto toDto(Tile dao) {
        return modelMapper.map(dao, TileDto.class);
    }

    public SlotDto toDto(Slot dao) {
        return modelMapper.map(dao, SlotDto.class);
    }

    public RackDto toDto(Rack dao) {
        return modelMapper.map(dao, RackDto.class);
    }

    public ChallengeDto toDto(Challenge dao) {
        return modelMapper.map(dao, ChallengeDto.class);
    }

    public UserSummaryDto toSummaryDto(User dao) {
        return modelMapper.map(dao, UserSummaryDto.class);
    }

    public UserDto toDto(User dao) {
        return modelMapper.map(dao, UserDto.class);
    }

    public PlayerDto toDto(Player dao) {
        return modelMapper.map(dao, PlayerDto.class);
    }


    public SideDto toDto(Side dao) {
        return modelMapper.map(dao, SideDto.class);
    }

    public OtherSideDto toOtherDto(Side dao) {
        return modelMapper.map(dao, OtherSideDto.class);
    }

    public GameDto toDto(Game dao, Player viewer) {
        Side mySide = dao.getSideOfPlayer(viewer);
        Side otherSide = dao.getSideOfPlayer(dao.getOtherPlayer(viewer));

        GameDto dto = modelMapper.map(dao, GameDto.class);
        dto.setMySide(modelMapper.map(
                mySide, SideDto.class));
        dto.setOtherSide(modelMapper.map(
                otherSide, OtherSideDto.class));
        dto.setMyTurn(Objects.equals(dao.getCurrPlayer().getId(), viewer.getId()));

        // If it's the viewer turn, then he sees the last completed challenge (if it exists) of his
        // adversary.
        List<Answer> otherAnswers = otherSide.getAnswers();
        if(dto.isMyTurn() && !otherAnswers.isEmpty()) {
            Challenge lastChallenge = otherAnswers.get(otherAnswers.size() - 1).getChallenge();
            dto.getOtherSide().setChallenge(modelMapper.map(lastChallenge, ChallengeDto.class));
        }

        return dto;
    }

    public GameSummaryDto toSummaryDto(Game dao, Player viewer) {
        GameSummaryDto dto = modelMapper.map(dao, GameSummaryDto.class);
        dto.setOtherPlayer(modelMapper.map(dao.getOtherPlayer(viewer), PlayerDto.class));
        return dto;
    }

    public Tile fromDto(TileDto dto) {
        // TODO: check with LangSet
        return modelMapper.map(dto, Tile.class);
    }

    public Slot fromDto(SlotDto dto) {
        return modelMapper.map(dto, Slot.class);
    }

    public Rack fromDto(RackDto dto) {
        return modelMapper.map(dto, Rack.class);
    }

    public Challenge fromDto(ChallengeDto dto) {
        return modelMapper.map(dto, Challenge.class);
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
