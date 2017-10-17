package ch.heigvd.wordoff.logic;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerRackTest {
    static ArrayList<Tile> listeTile = new ArrayList();

    @BeforeClass
    public static void init() {
        listeTile.add(new Tile(0, 'a', 1));
        listeTile.add(new Tile(1, 'b', 1));
        listeTile.add(new Tile(2, 'c', 1));
        listeTile.add(new Tile(3, 'd', 2));
        listeTile.add(new Tile(4, 'e', 1));
        listeTile.add(new Tile(5, 'f', 4));
        listeTile.add(new Tile(6, 'g', 3));
        listeTile.add(new Tile(7, 'h', 4));
    }

    @Test
    public void testAddTile() {
        PlayerRack pRack = new PlayerRack();
        // Vérifier si vide
        assertTrue(pRack.isEmpty());

        // Ajouter une tuile
        assertTrue(pRack.addTile(listeTile.get(0)));
        assertEquals(1, pRack.getRack().size());
        assertEquals(listeTile.get(0), pRack.getRack().get(0));

        // Tentative d'ajouter la même Tuile
        assertFalse(pRack.addTile(listeTile.get(0)));

        // Ajout du 2ème et 7ème Tuile
        for (int i = 1; i < 7; i++) {
            assertTrue(pRack.addTile(listeTile.get(i)));
        }
        assertEquals(7, pRack.getRack().size());

        // Ajout d'une 8ème tuile
        assertFalse(pRack.addTile(listeTile.get(7)));
    }

    @Test
    public void testIsEmpty() {
        PlayerRack pRack = new PlayerRack();
        assertTrue(pRack.isEmpty());
        pRack.addTile(listeTile.get(0));
        assertFalse(pRack.isEmpty());
    }

    @Test
    public void testGetSizeRack() {
        PlayerRack pRack = new PlayerRack();
        assertEquals(7, pRack.getSizeRack());
        pRack.addTile(listeTile.get(0));
        assertEquals(7, pRack.getSizeRack());
    }

    @Test
    public void testGetRack() {
        PlayerRack pRack = new PlayerRack();

        // Rack vide
        assertEquals(0, pRack.getRack().size());

        for (int i = 0; i < 7; i++) {
            pRack.addTile(listeTile.get(i));
        }

        // Rack non vide, vérification du contenu
        int i = 0;
        for (Tile t : pRack.getRack()) {
            assertEquals(t, listeTile.get(i++));
        }

        // Vérification limite du rack
        assertFalse(pRack.addTile(listeTile.get(7)));
    }

    @Test
    public void testGetTile() {
        PlayerRack pRack = new PlayerRack();
        assertEquals(null, pRack.getTile(listeTile.get(0).getId()));

        pRack.addTile(listeTile.get(3));

        Tile t = pRack.getTile(listeTile.get(3).getId());
        assertEquals(listeTile.get(3), t);
    }
}