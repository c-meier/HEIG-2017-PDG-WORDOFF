package ch.heigvd.wordoff.server.Model.Tiles;

import javax.persistence.*;
import java.util.List;

@Entity
public class LangSet {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    /**
     * When getting a LangSet from DB we want always want all the composing tiles (FetchType.EAGER)
     */
    @OneToMany(targetEntity = Tile.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name = "lang_set_id")
    private List<Tile> tiles;

    @OneToMany(targetEntity = Letter.class, cascade = CascadeType.ALL )
    @JoinColumn(name = "lang_set_id")
    private List<Letter> letters;

    protected LangSet() {}

    public LangSet(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public Tile getTileByValue(char value) {
        return getTileByValue(value, 0);
    }

    public Tile getTileByValue(char value, int occurence) {
        int match = 0;
        for(Tile t : getTiles()) {
            if(t.getValue() == value) {
                if(match == occurence) {
                    return t;
                } else {
                    match++;
                }
            }
        }
        return null;
    }
}
