package ch.heigvd.wordoff.common.Cases;

import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import org.junit.BeforeClass;

public class SlotTest {
    static TileDto tile;

    @BeforeClass
    public static void init(){
        tile = new TileDto(0, 'a',1);
    }

//    @Test
//    public void testAddTile(){
//        SlotDto slot = new SlotDto();
//        assertTrue(slot.isEmpty());
//        slot.addTile(tile);
//        assertFalse(slot.isEmpty());
//    }
//
//    @Test
//    public void testIsEmpty(){
//        SlotDto slot = new SlotDto();
//        assertTrue(slot.isEmpty());
//        slot.addTile(tile);
//        assertFalse(slot.isEmpty());
//    }
//
//    @Test
//    public void testRemoveTile(){
//        SlotDto slot = new SlotDto();
//        slot.addTile(tile);
//        assertFalse(slot.isEmpty());
//
//        Assert.assertEquals(tile,slot.removeTile());
//        assertTrue(slot.isEmpty());
//    }
//
//    @Test
//    public void testGetScore(){
//        SlotDto slot = new SlotDto();
//        // Score retourner 0, slot vide
//        assertEquals(0,slot.getScore());
//
//        // Slot de base, retourne score de la tuile
//        slot.addTile(tile);
//        Assert.assertEquals(tile.getScore(),slot.getScore());
//    }
}
