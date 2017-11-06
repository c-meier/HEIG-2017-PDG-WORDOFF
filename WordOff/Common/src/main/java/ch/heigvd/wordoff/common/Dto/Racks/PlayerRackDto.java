package ch.heigvd.wordoff.common.Dto.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.List;

public class PlayerRackDto extends RackDto {
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
}
