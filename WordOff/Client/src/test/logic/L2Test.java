import ch.heigvd.wordoff.logic.L2;
import ch.heigvd.wordoff.logic.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class L2Test {
    static Tile tile;

    @BeforeClass
    public static void init(){
        tile = new Tile(3,'d',4);
    }

    @Test
    public void testGetScore(){
        L2 l2 = new L2();
        // Cas vide score = 0
        assertEquals(0,l2.getScore());

        // Cas non vide score = score de la tuile *2
        l2.addTile(tile);
        assertEquals(tile.getScore()*2, l2.getScore());

    }

}
