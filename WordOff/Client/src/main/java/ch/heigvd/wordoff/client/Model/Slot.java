package ch.heigvd.wordoff.client.Model;

import javafx.scene.image.Image;

public class Slot extends ch.heigvd.wordoff.common.Model.Slots.Slot implements ISlot{

    public Slot(){
        super();
    }

    public Image getImage(){
        return new Image("/images/slot.png");
    }

}
