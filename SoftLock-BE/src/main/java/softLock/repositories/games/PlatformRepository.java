package softLock.repositories.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import softLock.entities.games.Platform;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findByNameAllIgnoreCase(String name);

    @Query("select p from Platform p where p.igdbID = ?1")
    Optional<Platform> findByIgdbID(Long igdbID);

    Optional<Platform> findBySlugIgnoreCase(String slug);

    Optional<Platform> findByAbbreviationIgnoreCase(String abbreviation);

    @Query("""
            select p from Platform p
            where upper(p.name) like upper(concat('%', ?1, '%')) or upper(p.slug) like upper(concat('%', ?2, '%')) or upper(p.abbreviation) like upper(concat('%', ?3, '%')) or p.igdbID = ?4""")
    Iterable<Platform> findByNameOrSlugOrAbbreviationOrIgdbID(@Nullable String name, @Nullable String slug, @Nullable String abbreviation, @Nullable Long igdbID);

}
