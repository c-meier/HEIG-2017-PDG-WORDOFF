package ch.heigvd.wordoff.common.Model.Tiles;

import javax.persistence.*;
import java.util.List;

public class TileSetDto {
    private Integer id;

    private String name;

    private List<TileDto> tiles;

    protected TileSetDto() {}

    public TileSetDto(String name) {
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

    public List<TileDto> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileDto> tiles) {
        this.tiles = tiles;
    }
}
