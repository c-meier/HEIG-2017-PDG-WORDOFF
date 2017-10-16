import ch.heigvd.wordoff.logic.PlayerRack;
import ch.heigvd.wordoff.logic.Tile;
import javafx.collections.ObservableList;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerRackTest {
    static PlayerRack spRackEmpty;
    static PlayerRack spRack;
    static ArrayList<Tile> listeTile = new ArrayList();

    @BeforeClass
    public static void init(){
        spRackEmpty = new PlayerRack();
        spRack = new PlayerRack();
        listeTile.add(new Tile(0,'a',1));
        listeTile.add(new Tile(1,'b',1));
        listeTile.add(new Tile(2,'c',1));
        listeTile.add(new Tile(3,'d',2));
        listeTile.add(new Tile(4,'e',1));
        listeTile.add(new Tile(5,'f',4));
        listeTile.add(new Tile(6,'g',3));
        listeTile.add(new Tile(7,'h',4));
    }

    @Test
    public void testAddTile(){
        // Vérifier si vide
        assertTrue(spRack.isEmpty());

        // Ajouter une tuile
        assertTrue(spRack.addTile(listeTile.get(0)));
        assertEquals(1,spRack.getRack().size());
        assertEquals(listeTile.get(0), spRack.getRack().get(0));

        // Tentative d'ajouter la même Tuile
        assertFalse(spRack.addTile(listeTile.get(0)));

        // Ajout du 2ème et 7ème Tuile
       for(int i = 1; i < 7; i++){
           assertTrue(spRack.addTile(listeTile.get(i)));
       }
       assertEquals(7,spRack.getRack().size());

        // Ajout d'une 8ème tuile
        assertFalse(spRack.addTile(listeTile.get(7)));
    }

    @Test
    public void testIsEmpty(){
        assertTrue(spRackEmpty.isEmpty());
        assertFalse(spRack.isEmpty());
    }

    @Test
    public void testGetSizeRack(){
        assertEquals(7,spRackEmpty.getSizeRack());
        assertEquals(7,spRack.getSizeRack());
    }

    @Test
    public void testGetRack(){
        int i = 0;
        for(Tile t : spRack.getRack()){
            assertEquals(t,listeTile.get(i++));
        }

        spRack.getTile(listeTile.get(2).getId());
        listeTile.remove(2);

        i = 0;
        for(Tile t : spRack.getRack()){
            assertEquals(t,listeTile.get(i++));
        }
    }

    @Test
    public void testGetTile(){
        Tile t = spRack.getTile(listeTile.get(0).getId());
        assertEquals(listeTile.get(0),t);
    }
}
