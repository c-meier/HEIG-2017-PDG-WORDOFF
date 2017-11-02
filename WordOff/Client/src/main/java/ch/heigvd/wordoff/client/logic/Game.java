package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.Model.Tiles.TileDto;

import java.util.Arrays;

public class Game {
    private Side sideP1;
    private Side sideP2;

    public Game() {
        this.sideP1 = new Side();
        this.sideP2 = new Side();
        // Tile int id, char value, int score
        this.sideP1.majRack(
                Arrays.asList(new TileDto(0, 'a', 1),
                        new TileDto(1, 'b', 3),
                        new TileDto(2, 'c', 3),
                        new TileDto(3, 'd', 2),
                        new TileDto(4, 'e', 2),
                        new TileDto(5, 'f', 4),
                        new TileDto(6, 'g', 2)),
                Arrays.asList(
                        new TileDto(7, 'h', 4),
                        new TileDto(8, 'i', 1)));

        this.sideP2.majRack(
                Arrays.asList(
                        new TileDto(10, 'j', 8),
                        new TileDto(11, 'k', 10),
                        new TileDto(12, 'l', 1),
                        new TileDto(13, 'm', 2),
                        new TileDto(14, 'n', 1),
                        new TileDto(15, 'o', 1),
                        new TileDto(16, 'p', 3)),
                Arrays.asList(
                        new TileDto(17, 'q', 8),
                        new TileDto(18, 'r', 1)));

    }

    public Side getSideP1() {
        return sideP1;
    }

    public Side getSideP2() {
        return sideP2;
    }
}
