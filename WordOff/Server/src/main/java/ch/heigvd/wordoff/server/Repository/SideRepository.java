package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Side;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SideRepository extends JpaRepository<Side, Long> {
}
