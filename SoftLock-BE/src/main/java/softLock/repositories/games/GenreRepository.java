package softLock.repositories.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import softLock.entities.games.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByNameAllIgnoreCase(String name);

    @Query("select g from Genre g where g.igdbID = ?1")
    Optional<Genre> findByIgdbID(Long igdbID);

    Optional<Genre> findBySlugIgnoreCase(String slug);

    @Query("""
            select g from Genre g
            where upper(g.name) like upper(concat('%', ?1, '%')) or upper(g.slug) like upper(concat('%', ?2, '%')) or g.igdbID = ?3""")
    Iterable<Genre> findByNameOrSlugOrIgdbID(@Nullable String name, @Nullable String slug, @Nullable Long igdbID);



}
