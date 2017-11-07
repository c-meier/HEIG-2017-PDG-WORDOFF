package ch.heigvd.wordoff.server.Model.Tiles;

import javax.persistence.*;
import java.util.List;

@Entity
public class TileSet {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    /**
     * When getting a TileSet from DB we want always want all the composing tiles (FetchType.EAGER)
     */
    @OneToMany(targetEntity = Tile.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tileSet")
    private List<Tile> tiles;

    protected TileSet() {}

    public TileSet(String name) {
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
}
