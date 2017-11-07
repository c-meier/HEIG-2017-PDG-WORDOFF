package ch.heigvd.wordoff.common.Cases;

import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import org.junit.*;

public class SevenThTest {
    static TileDto tile;

    @BeforeClass
    public static void init(){
        tile = new TileDto(3,'d',4);
    }

//    @Test
//    public void testGetScore(){
//        LastSlotDto s7 = new LastSlotDto();
//        // Cas vide score = 0
//        assertEquals(0,s7.getScore());
//
//        // Cas non vide score = score de la tuile *2
//        s7.addTile(tile);
//        Assert.assertEquals(tile.getScore()+10, s7.getScore());
//
//    }

}