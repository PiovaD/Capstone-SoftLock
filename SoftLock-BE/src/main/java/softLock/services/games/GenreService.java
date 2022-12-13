package softLock.services.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import softLock.entities.games.Genre;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.repositories.games.GenreRepository;

import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    GenreRepository rep;

    /**
     * Method to save and persist in db a Genre entity
     *
     * @param genre new genre
     * @return genre saved in the DB
     */
    public Genre save(Genre genre) {
        return rep.save(genre);
    }

    /**
     * Get All Genres, return an iterable of Genre
     *
     * @return All genres in the DB
     */
    public Iterable<Genre> getAllGenres() {
        return rep.findAll();
    }

    /**
     * Get All Genres, return a pageable of genre for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The genres in the DB paginated
     */
    public Page<Genre> getAllGenresPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     *
     * @param id Genre ID
     * @return The corresponding game
     */
    public Genre findByID(Long id) throws ByIdNotFoundException {
        Optional<Genre> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Genre", id);
    }

    /**
     * Find by name, if name is non-existent throws an exception
     *
     * @param name The exact name of the genre
     * @return The corresponding genre
     */
    public Genre findByName(String name) throws ByNameNotFoundException {
        Optional<Genre> found = rep.findByNameAllIgnoreCase(name);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Genre", name);
    }

    /**
     * @param igdbId Genre IGDB ID (the ID of the external DB)
     * @return The corresponding genre
     */
    public Genre findByIgdbID(long igdbId) {
        Optional<Genre> found = rep.findByIgdbID(igdbId);
        return found.orElse(null);
    }

    /**
     * @param slug The slug of the genre
     * @return The corresponding genre
     */
    public Genre findBySlug(String slug) throws ByNameNotFoundException {
        Optional<Genre> found = rep.findBySlugIgnoreCase(slug);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Game", slug);
    }

    /**
     * Search all genres with properties that match name and platform
     *
     * @param name   Genre name
     * @param slug   Genre slug
     * @param igdbID Genre IGDBID
     * @return All genres that meet the parameters
     */
    public Iterable<Genre> searchGenres(@Nullable String name, @Nullable String slug, @Nullable Long igdbID) {
        return rep.findByNameOrSlugOrIgdbID(name, slug, igdbID);

    }
}
