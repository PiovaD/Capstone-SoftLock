package softLock.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import softLock.entities.games.Genre;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.services.games.GenreService;

@Slf4j
@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:4200/")
public class GenreController {

    @Autowired
    GenreService serv;

    /*---------------------GET---------------------*/

    /**
     * @return All genres in the DB
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Genre>> getAllGenres() {
        return new ResponseEntity<>(serv.getAllGenres(), HttpStatus.OK);
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The genres in the DB paginated
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<Genre>> getAllGenresPageable(Pageable p) {
        Page<Genre> foundAll = serv.getAllGenresPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Genre ID
     * @return The corresponding game
     */
    @GetMapping("/id")
    public ResponseEntity<Genre> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(serv.findByID(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Genre IGDB ID (the ID of the external DB)
     * @return The corresponding genre
     */
    @GetMapping("/igdb-id")
    public ResponseEntity<Genre> findByIGDBId(@RequestParam(name = "id") Long id) {
        try {
            ResponseEntity<Genre> genre = new ResponseEntity<>(serv.findByIgdbID(id), HttpStatus.OK);
            if (genre.getBody() == null) throw new ByIdNotFoundException("igdb-id", id);

            return genre;

        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param name The exact name of the genre
     * @return The corresponding genre
     */
    @GetMapping("/exact-name")
    public ResponseEntity<Genre> findByExactName(@RequestParam(name = "name") String name) {
        try {
            return new ResponseEntity<>(serv.findByName(name), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param slug The slug of the genre
     * @return The corresponding genre
     */
    @GetMapping("/genre/{slug}")
    public ResponseEntity<Genre> findBySlug(@PathVariable("slug") String slug) {
        try {
            return new ResponseEntity<>(serv.findBySlug(slug), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param name Genre name
     * @param slug Genre slug
     * @param igdbID Genre IGDBID
     * @return All genres that meet the parameters
     */
    @GetMapping("/find")
    public ResponseEntity<Iterable<Genre>> searchGenres(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "slug", required = false) String slug,
            @RequestParam(name = "igdbID", required = false) Long igdbID
    ) {
        return new ResponseEntity<>(serv.searchGenres(name, slug, igdbID), HttpStatus.OK);
    }
}
