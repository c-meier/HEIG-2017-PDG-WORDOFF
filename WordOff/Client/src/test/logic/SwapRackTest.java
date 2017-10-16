import ch.heigvd.wordoff.logic.SwapRack;
import ch.heigvd.wordoff.logic.Tile;
import javafx.collections.ObservableList;
import org.junit.*;
import static org.junit.Assert.*;

public class SwapRackTest {
    static SwapRack spRackEmpty;
    static SwapRack spRack;
    static Tile tile = new Tile(0,'c',1);
    static Tile tile2 = new Tile(1,'a',1);

    @BeforeClass
    public static void init(){
        spRackEmpty = new SwapRack();
        spRack = new SwapRack();
    }

    @Test
    public void testAddTile(){
        // Vérifier si vide
        assertTrue(spRack.isEmpty());

        // Ajouter une tuile
        assertTrue(spRack.addTile(tile));
        assertEquals(1,spRack.getRack().size());
        assertEquals(tile, spRack.getRack().get(0));

        // Tentative d'ajouter la même Tuile
        assertFalse(spRack.addTile(tile));

        // Ajout du 2ème et 3ème Tuile
        assertTrue(spRack.addTile(tile2));
        assertFalse(spRack.addTile(new Tile(5,'d',2)));
    }

    @Test
    public void testIsEmpty(){
        assertTrue(spRackEmpty.isEmpty());
        assertFalse(spRack.isEmpty());
    }

    @Test
    public void testGetSizeRack(){
        assertEquals(2,spRackEmpty.getSizeRack());
        assertEquals(2,spRack.getSizeRack());
    }

    @Test
    public void testGetRack(){
        ObservableList<Tile> list = spRack.getRack();
        assertEquals(tile,list.get(0));
        assertEquals(tile2,list.get(1));
    }

    @Test
    public void testApplyBonus(){
        int score = 20;
        assertEquals(20*2,spRackEmpty.applyBonus(score));
        assertEquals(20-tile.getScore()-tile2.getScore(),spRack.applyBonus(score));
    }
    @Test

    public void testGetTile(){
        Tile t = spRack.getTile(tile.getId());
        assertEquals(tile, t);
    }
}
