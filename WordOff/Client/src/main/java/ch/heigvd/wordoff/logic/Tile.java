package ch.heigvd.wordoff.logic;

public class Tile {
    private int id;
    private char value;

    public Tile(int id, char value){
        this.id = id;
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public int getId() {
        return id;
    }
}
