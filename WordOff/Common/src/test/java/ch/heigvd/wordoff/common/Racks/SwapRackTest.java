package ch.heigvd.wordoff.common.Racks;

import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class SwapRackTest {
    static Tile tile;
    static Tile tile2;

    @BeforeClass
    public static void init() {
        tile = new Tile(0, 'c', 1);
        tile2 = new Tile(1, 'a', 1);
    }

    @Test
    public void testAddTile() {
        SwapRack spRack = new SwapRack();
        // Vérifier si vide
        assertTrue(spRack.isEmpty());

        // Ajouter une tuile
        assertTrue(spRack.addTile(tile));
        assertEquals(1, spRack.getRack().size());
        assertEquals(tile, spRack.getRack().get(0));

        // Tentative d'ajouter la même Tuile
        assertFalse(spRack.addTile(tile));

        // Ajout du 2ème
        assertTrue(spRack.addTile(tile2));
        assertEquals(2, spRack.getRack().size());

        // Ajout d'une 3ème tuile
        assertFalse(spRack.addTile(new Tile(3, 'c', 2)));
    }

    @Test
    public void testIsEmpty() {
        SwapRack spRack = new SwapRack();
        assertTrue(spRack.isEmpty());
        spRack.addTile(tile);
        assertFalse(spRack.isEmpty());
    }

    @Test
    public void testGetSizeRack() {
        SwapRack spRack = new SwapRack();
        assertEquals(2, spRack.getMaxSizeRack());
        spRack.addTile(tile);
        assertEquals(2, spRack.getMaxSizeRack());
    }

    @Test
    public void testGetRack() {
        SwapRack spRack = new SwapRack();

        // Rack vide
        assertEquals(0, spRack.getRack().size());

        spRack.addTile(tile);
        spRack.addTile(tile2);


        // Rack non vide, vérification du contenu
        assertEquals(tile, spRack.getRack().get(0));
        assertEquals(tile2, spRack.getRack().get(1));

        // Vérification limite du rack
        assertFalse(spRack.addTile(new Tile(4, 'd', 3)));
    }

    @Test
    public void testGetTile() {
        // Rack vide
        SwapRack spRack = new SwapRack();
        assertEquals(null, spRack.getTile(tile.getId()));

        spRack.addTile(tile);

        Tile t = spRack.getTile(tile.getId());
        assertEquals(tile, t);
    }

    @Test
    public void testApplyBonus() {
        SwapRack spRack = new SwapRack();
        int score = 20;

        // Cas vide
        assertEquals(20 * 2, spRack.applyBonus(score));

        // Cas non vide
        spRack.addTile(tile);
        spRack.addTile(tile2);
        assertEquals(20 - tile.getScore() - tile2.getScore(), spRack.applyBonus(score));
    }

}
