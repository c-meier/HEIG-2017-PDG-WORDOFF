package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.common.Constants;

import javax.persistence.AssociationOverride;
import javax.persistence.Embeddable;
import javax.persistence.JoinTable;
import java.util.List;

@Embeddable
@AssociationOverride(
        name="tiles",
        joinTable=@JoinTable(
                name="SwapRack"
        )
)
public class SwapRack extends Rack {
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
