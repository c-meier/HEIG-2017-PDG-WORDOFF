package ch.heigvd.wordoff.client.Racks;

import ch.heigvd.wordoff.common.logic.Tile;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class RackObservableTest {
    static ArrayList<Tile> listeTiles = new ArrayList<>();

    @BeforeClass
    public static void init() {
        listeTiles.add(new Tile(0, 'a', 1));
        listeTiles.add(new Tile(1, 'b', 1));
        listeTiles.add(new Tile(2, 'c', 1));
        listeTiles.add(new Tile(3, 'd', 2));
        listeTiles.add(new Tile(4, 'e', 1));
        listeTiles.add(new Tile(5, 'f', 4));
        listeTiles.add(new Tile(6, 'g', 3));
    }

    @Test
    public void testInitRack() {
        RackObservable rackO = new RackObservable(7);
        rackO.initRack(listeTiles);
        assertArrayEquals(listeTiles.toArray(), rackO.getRackObservable().toArray());
    }

    @Test
    public void testGetTile() {
        RackObservable rackO = new RackObservable(7);
        rackO.initRack(listeTiles);
        Tile tile = rackO.getTile(listeTiles.get(2).getId());

        // Tuile existante et vérification du retrait dans la liste
        assertEquals(listeTiles.get(2), tile);
        assertEquals(6, rackO.getRackObservable().size());

        // Tuile non existante dans le rack
        assertNull(rackO.getTile(9));
    }

    @Test
    public void testGetSizeMax() {
        RackObservable rackO = new RackObservable(7);
        assertEquals(7, rackO.getMaxSizeRack());
    }

    @Test
    public void testAddTile() {
        RackObservable rackO = new RackObservable(7);
        // Avant insertion sur liste vide
        assertTrue(rackO.isEmpty());

        // Insertion d'une tuile
        assertTrue(rackO.addTile(listeTiles.get(0)));
        assertArrayEquals(listeTiles.subList(0,1).toArray(),rackO.getRackObservable().toArray());
        rackO.getTile(listeTiles.get(0).getId());

        // Insertion max
        assertTrue(rackO.initRack(listeTiles));
        assertArrayEquals(listeTiles.toArray(),rackO.getRackObservable().toArray());

        // Insertion quand plein
        assertFalse(rackO.addTile(new Tile(8,'l',3)));
    }

    @Test
    public void testIsEmpty(){
        RackObservable rackO = new RackObservable(7);
        // Avant insertion sur liste vide
        assertTrue(rackO.isEmpty());
        rackO.addTile(listeTiles.get(0));
        assertFalse(rackO.isEmpty());
    }

    @Test
    public void testGetRack(){
        RackObservable rackO = new RackObservable(7);
        rackO.addTile(listeTiles.get(0));
        rackO.addTile(listeTiles.get(3));

        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(listeTiles.get(0));
        expected.add(listeTiles.get(3));

        assertArrayEquals(expected.toArray(), rackO.getRackObservable().toArray());
    }
}