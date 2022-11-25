package softLock.services.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     */
    public Genre save(Genre genre) {
        return rep.save(genre);
    }

    /**
     * Get All Genres, return an iterable of Genre
     */
    public Iterable<Genre> getAllGenres() {
        return rep.findAll();
    }

    /**
     * Get All Genres, return a pageable of genre for lighter payloads
     */
    public Page<Genre> getAllGenresPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     */
    public Genre findById(Long id) throws ByIdNotFoundException {
        Optional<Genre> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Genre", id);
    }

    /**
     * Find by name, if name is non-existent throws an exception
     */
    public Genre findByName(String name) throws ByNameNotFoundException {
        Optional<Genre> found = rep.findByName(name);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Genre", name);
    }

    /**
     * update Genre
     */
    public Genre updateGenre(Genre updatedGenre) throws ByIdNotFoundException {
        rep.save(updatedGenre);
        return updatedGenre;
    }

    /**
     * throws IllegalArgumentException
     */
    public String deleteGenre(Long id) {
        rep.deleteById(id);
        return "Genre delete successfully";
    }

    public Genre findByIgdbId(long igdbId) {
        Optional<Genre> found = rep.findByIgdbID(igdbId);
        return found.orElse(null);
    }
}