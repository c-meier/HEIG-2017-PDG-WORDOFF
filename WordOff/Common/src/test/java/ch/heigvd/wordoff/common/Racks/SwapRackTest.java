/*
 * File: SwapRackTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Racks;

import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.IModel.ITile;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SwapRackTest {
    static TileDto tile;
    static TileDto tile2;

    @BeforeClass
    public static void init() {
        tile = new TileDto(0, 'c', 1);
        tile2 = new TileDto(1, 'a', 1);
    }

    @Test
    public void testAddTile() {
        SwapRackDto spRack = new SwapRackDto(new ArrayList<>());
        // Vérifier si vide
        assertTrue(spRack.isEmpty());

        // Ajouter une tuile
        assertTrue(spRack.addTile(tile));
        assertEquals(1, spRack.getTiles().size());
        assertEquals(tile, spRack.getTiles().get(0));

        // Tentative d'ajouter la même Tuile
        assertFalse(spRack.addTile(tile));

        // Ajout du 2ème
        assertTrue(spRack.addTile(tile2));
        assertEquals(2, spRack.getTiles().size());

        // Ajout d'une 3ème tuile
        assertFalse(spRack.addTile(new TileDto(3, 'c', 2)));
    }

    @Test
    public void testIsEmpty() {
        SwapRackDto spRack = new SwapRackDto(new ArrayList<>());
        assertTrue(spRack.isEmpty());
        spRack.addTile(tile);
        assertFalse(spRack.isEmpty());
    }

    @Test
    public void testGetSizeRack() {
        SwapRackDto spRack = new SwapRackDto(new ArrayList<>());
        assertEquals(2, spRack.getMaxSizeRack());
        spRack.addTile(tile);
        assertEquals(2, spRack.getMaxSizeRack());
    }

    @Test
    public void testGetRack() {
        SwapRackDto spRack = new SwapRackDto(new ArrayList<>());

        // Rack vide
        assertEquals(0, spRack.getTiles().size());

        spRack.addTile(tile);
        spRack.addTile(tile2);


        // Rack non vide, vérification du contenu
        assertEquals(tile, spRack.getTiles().get(0));
        assertEquals(tile2, spRack.getTiles().get(1));

        // Vérification limite du rack
        assertFalse(spRack.addTile(new TileDto(4,  'd', 3)));
    }

    @Test
    public void testGetTile() {
        // Rack vide
        SwapRackDto spRack = new SwapRackDto(new ArrayList<>());
        assertEquals(null, spRack.removeTile(tile.getId()));

        spRack.addTile(tile);

        ITile t = spRack.removeTile(tile.getId());
        assertEquals(tile, t);
    }

}
