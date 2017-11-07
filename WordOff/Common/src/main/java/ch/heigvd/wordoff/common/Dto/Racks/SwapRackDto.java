package ch.heigvd.wordoff.common.Dto.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.List;

public class SwapRackDto extends RackDto {
    public SwapRackDto(List<ITile> tiles) {
        super(tiles);
    }

    /**
     * Retourne la taille du rack
     *
     * @return
     */
    @Override
    public int getMaxSizeRack() {
        return Constants.SWAP_RACK_SIZE;
    }
}
