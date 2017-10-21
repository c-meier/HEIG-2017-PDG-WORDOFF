package ch.heigvd.wordoff.common.Cases;
import ch.heigvd.wordoff.common.logic.Tile;
public class Slot {
    private Tile tile;

    public Slot() {
        this.tile = null;
    }

    public boolean addTile(Tile tile) {
        if (isEmpty()) {
            this.tile = tile;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        if (null == tile) {
            return true;
        }
        return false;
    }

    public Tile removeTile() {
        Tile temp = tile;
        tile = null;
        return temp;
    }

    public Tile getTile(){
        return tile;
    }

    public int getScore() {
        return null == tile ? 0 : tile.getScore();
    }

}
