package ch.heigvd.wordoff.client.Model;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import javafx.scene.image.Image;

public interface ISlot {

    Image getImage();
    boolean addTile(Tile tile);
    boolean isEmpty();
    Tile removeTile();
    Tile getTile();
    int getScore();
}
