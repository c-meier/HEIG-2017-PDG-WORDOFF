package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.Constants;

public class SwapRack extends Rack {
    /**
     * Retourne la taille du rack
     *
     * @return
     */
    public SwapRack() {}

    @Override
    public int getMaxSizeRack() {
        return Constants.SWAP_RACK_SIZE;
    }
}
