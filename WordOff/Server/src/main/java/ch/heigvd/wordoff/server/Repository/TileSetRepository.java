package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Tiles.TileSet;
import org.springframework.data.repository.CrudRepository;

public interface TileSetRepository extends CrudRepository<TileSet, Integer> {
    TileSet findByName(String name);
}
