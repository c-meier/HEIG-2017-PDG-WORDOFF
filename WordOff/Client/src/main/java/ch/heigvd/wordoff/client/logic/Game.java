package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import java.util.Arrays;

public class Game {
    private Side sideP1;
    private Side sideP2;

    public Game() {
        this.sideP1 = new Side();
        this.sideP2 = new Side();
        // Tile int id, char value, int score
        this.sideP1.majRack(
                Arrays.asList(new Tile(0, 'a', 1),
                        new Tile(1, 'b', 3),
                        new Tile(2, 'c', 3),
                        new Tile(3, 'd', 2),
                        new Tile(4, 'e', 2),
                        new Tile(5, 'f', 4),
                        new Tile(6, 'g', 2)),
                Arrays.asList(
                        new Tile(7, 'h', 4),
                        new Tile(8, 'i', 1)));

        this.sideP2.majRack(
                Arrays.asList(
                        new Tile(10, 'j', 8),
                        new Tile(11, 'k', 10),
                        new Tile(12, 'l', 1),
                        new Tile(13, 'm', 2),
                        new Tile(14, 'n', 1),
                        new Tile(15, 'o', 1),
                        new Tile(16, 'p', 3)),
                Arrays.asList(
                        new Tile(17, 'q', 8),
                        new Tile(18, 'r', 1)));

    }

    public Side getSideP1() {
        return sideP1;
    }

    public Side getSideP2() {
        return sideP2;
    }
}
