package ch.heigvd.wordoff.Repository;

import ch.heigvd.wordoff.common.Model.Tiles.TileSet;
import org.springframework.data.repository.CrudRepository;

public interface TileSetRepository extends CrudRepository<TileSet, Integer> {
    TileSet findByName(String name);
}
