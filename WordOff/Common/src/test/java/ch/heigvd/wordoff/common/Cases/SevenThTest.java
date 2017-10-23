package ch.heigvd.wordoff.common.Cases;

import ch.heigvd.wordoff.common.Model.Slots.SevenTh;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class SevenThTest {
    static Tile tile;

    @BeforeClass
    public static void init(){
        tile = new Tile(3,'d',4);
    }

    @Test
    public void testGetScore(){
        SevenTh s7 = new SevenTh();
        // Cas vide score = 0
        assertEquals(0,s7.getScore());

        // Cas non vide score = score de la tuile *2
        s7.addTile(tile);
        Assert.assertEquals(tile.getScore()+10, s7.getScore());

    }

}