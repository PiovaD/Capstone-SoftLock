package softLock.repositories.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import softLock.entities.games.Game;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByNameAllIgnoreCase(String name);

    Optional<Game> findByIgdbID(Long igdbID);

    Optional<Game> findBySlugIgnoreCase(String slug);

    @Query("""
            select g from Game g left join g.genres genres left join g.platforms platforms
            where upper(genres.name) like upper(concat('%', ?1, '%')) or upper(platforms.abbreviation) like upper(concat('%', ?2, '%'))""")
    Set<Game> findByGenreOrPlatform(@Nullable String genresName, @Nullable String platformsAbbreviation);


}
