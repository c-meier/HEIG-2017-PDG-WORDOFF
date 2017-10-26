package ch.heigvd.wordoff.client.Model;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import javafx.scene.image.Image;

public class Swap extends ch.heigvd.wordoff.common.Model.Slots.Swap implements ISlot{

    public Image getImage(){
        return new Image("/images/swapSlot.png");
    }

}
