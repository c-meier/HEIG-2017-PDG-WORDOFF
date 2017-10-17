package ch.heigvd.wordoff.Racks;

import ch.heigvd.wordoff.logic.Racks.Rack;
import ch.heigvd.wordoff.logic.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RackObservable extends Rack {
    ObservableList<Tile> rack;

    public RackObservable(int sizeRack){
        super(sizeRack);
        rack = FXCollections.observableArrayList();
    }

    public ObservableList<Tile> getRackObservable() {
        majRack();
        return rack;
    }

    public boolean initRack(ArrayList<Tile> tiles){
        for(Tile t : tiles){
            if(!super.addTile(t)){
                return false;
            }
        }
        return true;
    }

    private void majRack(){
        rack.setAll(super.getRack());
    }

    @Override
    public boolean isEmpty(){
        return rack.isEmpty();
    }

    @Override
    public Tile getTile(int idTile){
        Tile tile = super.getTile(idTile);
        majRack();
        return tile;
    }

    @Override
    public boolean addTile(Tile tile){
       if(super.addTile(tile)){
            majRack();
            return true;
        }
        return false;
    }


    public int getMaxSize(){
        return super.getSizeRack();
    }
}
