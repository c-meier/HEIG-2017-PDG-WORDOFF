package ch.heigvd.wordoff.common.Cases;

import ch.heigvd.wordoff.common.Cases.L3;
import ch.heigvd.wordoff.common.logic.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class L3Test {
    static Tile tile;

    @BeforeClass
    public static void init(){
        tile = new Tile(3,'d',4);
    }

    @Test
    public void testGetScore(){
        L3 l3 = new L3();
        // Cas vide score = 0
        assertEquals(0,l3.getScore());

        // Cas non vide score = score de la tuile *2
        l3.addTile(tile);
        assertEquals(tile.getScore()*3, l3.getScore());
    }
}
