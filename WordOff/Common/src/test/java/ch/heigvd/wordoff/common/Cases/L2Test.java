package ch.heigvd.wordoff.common.Cases;


import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import org.junit.BeforeClass;

public class L2Test {
    static TileDto tile;

    @BeforeClass
    public static void init(){
        tile = new TileDto(3,'d',4);
    }

//    @Test
//    public void testGetScore(){
//        L2SlotDto l2 = new L2SlotDto();
//        // Cas vide score = 0
//        assertEquals(0,l2.getScore());
//
//        // Cas non vide score = score de la tuile *2
//        l2.addTile(tile);
//        assertEquals(tile.getScore()*2, l2.getScore());
//
//    }

}