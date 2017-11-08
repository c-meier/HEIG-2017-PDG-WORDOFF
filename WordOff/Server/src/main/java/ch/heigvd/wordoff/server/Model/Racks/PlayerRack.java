package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.Constants;

public class PlayerRack extends Rack {

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
