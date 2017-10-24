package ch.heigvd.wordoff.Repository;

import ch.heigvd.wordoff.Model.Game;
import ch.heigvd.wordoff.common.Model.Side;
import org.springframework.data.repository.CrudRepository;

public interface SideRepository extends CrudRepository<Side, Long> {
}
