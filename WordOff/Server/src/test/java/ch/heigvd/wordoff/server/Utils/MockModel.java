package ch.heigvd.wordoff.server.Utils;

import ch.heigvd.wordoff.server.Model.Ai;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockModel {
    private LangSet langSet;
    private Ai ai;
    private User one;
    private User two;
    private Game duelGame;
    private Game aiGame;

    private int indexTile;

    public MockModel() {
        langSet = new LangSet("fr");
        langSet.setTiles(Arrays.asList(
                new Tile(1, '#', 0),
                new Tile(2, 'A', 1),
                new Tile(3, 'B', 4),
                new Tile(4, 'C', 3),
                new Tile(5, 'D', 5),
                new Tile(6, 'E', 1),
                new Tile(7, 'F', 3),
                new Tile(8, 'G', 2),
                new Tile(9, 'H', 2),
                new Tile(10, 'I', 1),
                new Tile(11, 'J', 8),
                new Tile(12, 'K', 6),
                new Tile(13, 'L', 2),
                new Tile(14, 'M', 1),
                new Tile(15, 'N', 2),
                new Tile(16, 'O', 1),
                new Tile(17, 'P', 2),
                new Tile(18, 'Q', 8),
                new Tile(19, 'R', 2),
                new Tile(20, 'S', 1),
                new Tile(21, 'T', 1),
                new Tile(22, 'U', 1),
                new Tile(23, 'V', 4),
                new Tile(24, 'W', 10),
                new Tile(25, 'X', 10),
                new Tile(26, 'Y', 10),
                new Tile(27, 'Z', 10)
        ));
        indexTile = 0;

        ai = new Ai();
        ai.setId(1L);

        one = new User("testUserOne");
        one.setId(2L);

        two = new User("testUserTwo");
        two.setId(3L);

        aiGame = new Game(one, ai, langSet);
        aiGame.setId(1L);
        aiGame.getSideInit().setId(1L);
        aiGame.getSideResp().setId(2L);

        duelGame = new Game(one, two, langSet);
        duelGame.setId(2L);
        duelGame.getSideInit().setId(3L);
        duelGame.getSideResp().setId(4L);
    }

    public LangSet getLangSet() {
        return langSet;
    }

    public Ai getAi() {
        return ai;
    }

    public User getUserOne() {
        return one;
    }

    public User getUserTwo() {
        return two;
    }

    public Tile getTile() {
        List<Tile> tiles = langSet.getTiles();
        indexTile = indexTile % tiles.size();
        return tiles.get(indexTile++);
    }

    public Game getDuelGame() {
        return duelGame;
    }

    public Game getAiGame() {
        return aiGame;
    }
}
