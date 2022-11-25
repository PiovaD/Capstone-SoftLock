package softLock.repositories.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softLock.entities.games.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

    @Query("select g from Genre g where g.igdbID = ?1")
    Optional<Genre> findByIgdbID(Long igdbID);


}
