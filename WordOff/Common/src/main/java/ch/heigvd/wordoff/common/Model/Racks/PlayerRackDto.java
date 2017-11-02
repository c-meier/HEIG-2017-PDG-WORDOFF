package ch.heigvd.wordoff.common.Model.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;

import javax.persistence.*;
import java.util.List;

public class PlayerRackDto extends RackDto {
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
