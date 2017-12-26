package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllBySideInitPlayerId(Long id);
    List<Game> findAllBySideRespPlayerId(Long id);
    List<Game> findAllBySideInitPlayerIdOrSideRespPlayerId(Long idInit, Long idResp);

    default List<Game> retrieveAllByOnePlayerId(@Param("playerId") Long id) {
        return findAllBySideInitPlayerIdOrSideRespPlayerId(id, id);
    }
}
