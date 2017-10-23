package ch.heigvd.wordoff.common.Model.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javax.persistence.*;
import java.util.List;

@Embeddable
@AssociationOverride(
        name="tiles",
        joinTable=@JoinTable(
                name="PlayerRack"
        )
)
public class PlayerRack extends Rack {

    /**
     * Constructeur
     */
    public PlayerRack() {
        super();
    }

    /**
     * Retourne si le rack est vide
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Retirer une tuile du rack
     *
     * @param idTile id de la tuile à retirer
     * @return
     */
    @Override
    public Tile getTile(int idTile) {
        return super.getTile(idTile);
    }

    /**
     * Ajouter une tuile, confirmation.
     *
     * @param tile tuile à ajouter
     */
    @Override
    public boolean addTile(Tile tile) {
        return super.addTile(tile);
    }

    /**
     * Retourne la liste du rack
     *
     * @return
     */
    @Override
    public List<Tile> getRack() {
        return super.getRack();
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
