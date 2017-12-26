package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LangSetRepository extends JpaRepository<LangSet, Integer> {
    LangSet findByName(String name);
}
