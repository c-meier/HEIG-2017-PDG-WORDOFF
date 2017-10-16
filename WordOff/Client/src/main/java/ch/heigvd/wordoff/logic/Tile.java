package ch.heigvd.wordoff.logic;

public class Tile {
    private final int id;
    private final char value;
    private final int score;

    public Tile(int id, char value, int score) {
        this.id = id;
        this.value = value;
        this.score = score;
    }

    public char getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public int getScore(){
        return score;
    }
}
