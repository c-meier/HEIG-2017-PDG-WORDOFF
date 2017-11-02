package ch.heigvd.wordoff.common.Cases;

import ch.heigvd.wordoff.common.Model.Slots.L3SlotDto;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;
import org.junit.*;
import static org.junit.Assert.*;

public class L3Test {
    static TileDto tile;

    @BeforeClass
    public static void init(){
        tile = new TileDto(3,'d',4);
    }

    @Test
    public void testGetScore(){
        L3SlotDto l3 = new L3SlotDto();
        // Cas vide score = 0
        assertEquals(0,l3.getScore());

        // Cas non vide score = score de la tuile *2
        l3.addTile(tile);
        assertEquals(tile.getScore()*3, l3.getScore());
    }
}
