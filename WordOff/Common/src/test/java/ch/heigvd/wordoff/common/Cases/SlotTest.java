package ch.heigvd.wordoff.common.Cases;
import ch.heigvd.wordoff.common.Model.Slots.Slot;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class SlotTest {
    static Tile tile;

    @BeforeClass
    public static void init(){
        tile = new Tile(0,'a',1);
    }

    @Test
    public void testAddTile(){
        Slot slot = new Slot();
        assertTrue(slot.isEmpty());
        slot.addTile(tile);
        assertFalse(slot.isEmpty());
    }

    @Test
    public void testIsEmpty(){
        Slot slot = new Slot();
        assertTrue(slot.isEmpty());
        slot.addTile(tile);
        assertFalse(slot.isEmpty());
    }

    @Test
    public void testRemoveTile(){
        Slot slot = new Slot();
        slot.addTile(tile);
        assertFalse(slot.isEmpty());

        Assert.assertEquals(tile,slot.removeTile());
        assertTrue(slot.isEmpty());
    }

    @Test
    public void testGetScore(){
        Slot slot = new Slot();
        // Score retourner 0, slot vide
        assertEquals(0,slot.getScore());

        // Slot de base, retourne score de la tuile
        slot.addTile(tile);
        Assert.assertEquals(tile.getScore(),slot.getScore());
    }
}
