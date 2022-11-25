package softLock.repositories.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softLock.entities.games.Platform;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findByName(String name);

    @Query("select p from Platform p where p.igdbID = ?1")
    Optional<Platform> findByIgdbID(Long igdbID);

}
