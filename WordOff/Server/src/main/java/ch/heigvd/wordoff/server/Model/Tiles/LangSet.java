package ch.heigvd.wordoff.server.Model.Tiles;

import javax.persistence.*;
import java.util.ArrayList;
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

    public LangSet(LangSet langSet) {
        this.id = langSet.id;
        this.letters = new ArrayList<>(langSet.getLetters());
        this.name = langSet.name;
        this.tiles = new ArrayList<>(langSet.getTiles());
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
}
