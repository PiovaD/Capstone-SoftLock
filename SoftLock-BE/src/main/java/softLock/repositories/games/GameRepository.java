package softLock.repositories.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softLock.entities.games.Game;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String name);

    Optional<Game> findByIgdbID(Long igdbID);
}
