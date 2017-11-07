package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Letter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LetterRepository extends CrudRepository<Letter, Integer> {
    List<Letter> findAllByLangSetName(String name);
}
