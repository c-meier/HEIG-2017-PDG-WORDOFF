package ch.heigvd.wordoff.common.Dto.Game.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.ITile;

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
    public boolean equals(Object o) {
        return o == this || o instanceof PlayerRackDto && super.equals(o);
    }
}
