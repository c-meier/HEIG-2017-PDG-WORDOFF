package ch.heigvd.wordoff.common.Racks;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerRackTest {
    static ArrayList<TileDto> listeTile = new ArrayList();

    @BeforeClass
    public static void init() {
        listeTile.add(new TileDto(0, 'a', 1));
        listeTile.add(new TileDto(1, 'b', 1));
        listeTile.add(new TileDto(2, 'c', 1));
        listeTile.add(new TileDto(3, 'd', 2));
        listeTile.add(new TileDto(4, 'e', 1));
        listeTile.add(new TileDto(5, 'f', 4));
        listeTile.add(new TileDto(6, 'g', 3));
        listeTile.add(new TileDto(7, 'h', 4));
    }

    @Test
    public void testAddTile() {
        PlayerRackDto pRack = new PlayerRackDto(new ArrayList<>());
        // Vérifier si vide
        assertTrue(pRack.isEmpty());

        // Ajouter une tuile
        assertTrue(pRack.addTile(listeTile.get(0)));
        assertEquals(1, pRack.getTiles().size());
        assertEquals(listeTile.get(0), pRack.getTiles().get(0));

        // Tentative d'ajouter la même Tuile
        assertFalse(pRack.addTile(listeTile.get(0)));

        // Ajout du 2ème et 7ème Tuile
        for (int i = 1; i < 7; i++) {
            assertTrue(pRack.addTile(listeTile.get(i)));
        }
        assertEquals(7, pRack.getTiles().size());

        // Ajout d'une 8ème tuile
        assertFalse(pRack.addTile(listeTile.get(7)));
    }

    @Test
    public void testIsEmpty() {
        PlayerRackDto pRack = new PlayerRackDto(new ArrayList<>());
        assertTrue(pRack.isEmpty());
        pRack.addTile(listeTile.get(0));
        assertFalse(pRack.isEmpty());
    }

    @Test
    public void testGetSizeRack() {
        PlayerRackDto pRack = new PlayerRackDto(new ArrayList<>());
        assertEquals(7, pRack.getMaxSizeRack());
        pRack.addTile(listeTile.get(0));
        assertEquals(7, pRack.getMaxSizeRack());
    }

    @Test
    public void testGetRack() {
        PlayerRackDto pRack = new PlayerRackDto(new ArrayList<>());

        // Rack vide
        assertEquals(0, pRack.getTiles().size());

        for (int i = 0; i < 7; i++) {
            pRack.addTile(listeTile.get(i));
        }

        // Rack non vide, vérification du contenu
        int i = 0;
        for (ITile t : pRack.getTiles()) {
            assertEquals(t, listeTile.get(i++));
        }

        // Vérification limite du rack
        assertFalse(pRack.addTile(listeTile.get(7)));
    }

    @Test
    public void testGetTile() {
        PlayerRackDto pRack = new PlayerRackDto(new ArrayList<>());
        assertEquals(null, pRack.getTile(listeTile.get(0).getId()));

        pRack.addTile(listeTile.get(3));

        ITile t = pRack.getTile(listeTile.get(3).getId());
        assertEquals(listeTile.get(3), t);
    }
}