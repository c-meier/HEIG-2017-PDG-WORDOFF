package ch.heigvd.wordoff.common.Model.Tiles;

import javax.persistence.*;
import java.util.Collection;
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
    private Collection<Tile> tiles;

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

    public Collection<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TileSet: ");
        sb.append(name);
        sb.append("\n");

        if (getTiles() != null) {
            for (Tile tile : getTiles()) {
                sb.append("\t");
                try{
                    sb.append("id: ");
                    sb.append(tile.getId());
                } catch (NullPointerException npe) {
                    sb.append("NULL");
                }
                sb.append(", value: ");
                sb.append(tile.getValue());
                sb.append(", score: ");
                sb.append(tile.getScore());
                sb.append("\n");
            }
        } else {
            sb.append("\tNo tiles list");
        }

        return sb.toString();
    }
}
