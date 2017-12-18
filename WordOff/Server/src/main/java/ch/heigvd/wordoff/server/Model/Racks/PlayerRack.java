package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.ArrayList;
import java.util.List;

public class PlayerRack extends Rack {

    /**
     * Retourne la taille du rack
     *
     * @return
     */
    public PlayerRack() {
        super();
    }

    @Override
    public int getMaxSizeRack() {
        return Constants.PLAYER_RACK_SIZE;
    }

    @Override
    public IRack duplicate() {
        PlayerRack copy = new PlayerRack();
        List<ITile> tilesCopy = new ArrayList<>();
        getTiles().forEach((elem)->tilesCopy.add(elem.duplicate()));
        copy.setTiles(tilesCopy);
        return copy;
    }
}
