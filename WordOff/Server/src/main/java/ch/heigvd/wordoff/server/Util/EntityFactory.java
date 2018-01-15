package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.Dto.Game.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.*;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.Rack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.*;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

/**
 * Class that converts Dtos to Entities.
 */
@Component
public class EntityFactory {
    private static ModelMapper modelMapper = configuredModelMapper();

    private static ModelMapper configuredModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

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

        return modelMapper;

    }

    public static Tile createFrom(TileDto dto) {
        // TODO: check with LangSet
        return modelMapper.map(dto, Tile.class);
    }

    public static Slot createFrom(SlotDto dto) {
        return modelMapper.map(dto, Slot.class);
    }

    public static Rack createFrom(RackDto dto) {
        return modelMapper.map(dto, Rack.class);
    }

    public static Challenge createFrom(ChallengeDto dto) {
        return modelMapper.map(dto, Challenge.class);
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
