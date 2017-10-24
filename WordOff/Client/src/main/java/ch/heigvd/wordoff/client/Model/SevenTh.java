package ch.heigvd.wordoff.client.Model;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import javafx.scene.image.Image;

public class SevenTh extends ch.heigvd.wordoff.common.Model.Slots.SevenTh implements ISlot {

    private ch.heigvd.wordoff.common.Model.Slots.SevenTh sevenTh = new ch.heigvd.wordoff.common.Model.Slots.SevenTh();

    public Image getImage(){
        return new Image("/images/plus10Slot.png");
    }


}
