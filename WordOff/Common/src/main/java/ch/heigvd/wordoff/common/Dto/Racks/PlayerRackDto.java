package ch.heigvd.wordoff.common.Dto.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.ArrayList;
import java.util.List;

public class PlayerRackDto extends RackDto {
    // Necessary for Jackson deserialization
    public PlayerRackDto() {}

    public PlayerRackDto(List<ITile> tiles) {
        super(tiles);
    }

    /**
     * Retourne la taille du rack
     *
     * @return
     */
    @Override
    public int getMaxSizeRack() {
        return Constants.PLAYER_RACK_SIZE;
    }

    @Override
    public IRack duplicate() {
        List<ITile> tilesCopy = new ArrayList<>();
        getTiles().forEach((elem)->tilesCopy.add(elem.duplicate()));
        return new PlayerRackDto(tilesCopy);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof PlayerRackDto && super.equals(o);
    }
}
