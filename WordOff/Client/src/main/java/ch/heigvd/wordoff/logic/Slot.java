package ch.heigvd.wordoff.logic;

public class Slot {
    private Tile tile;

    public Slot(){

    }

    /**
     * Slot de base aucun effet
     * @return
     */
    public boolean apply(int score){
        return true;
    }

    public boolean addTile(){
        return true;
    }

    public Tile getTile(){
        return tile;
    }
}
