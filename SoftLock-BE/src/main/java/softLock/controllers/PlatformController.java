package softLock.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import softLock.entities.games.Platform;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.services.games.PlatformService;

@Slf4j
@RestController
@RequestMapping("/api/platforms")
@CrossOrigin(origins = "http://localhost:4200/")
public class PlatformController {

    @Autowired
    PlatformService serv;

    /*---------------------GET---------------------*/

    /**
     * @return All platforms in the DB
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Platform>> getAllPlatforms() {
        return new ResponseEntity<>(serv.getAllPlatforms(), HttpStatus.OK);
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The platforms in the DB paginated
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<Platform>> getAllPlatformsPageable(Pageable p) {
        Page<Platform> foundAll = serv.getAllPlatformsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Platform ID
     * @return The corresponding platform
     */
    @GetMapping("/id")
    public ResponseEntity<Platform> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(serv.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Platform IGDB ID (the ID of the external DB)
     * @return The corresponding platform
     */
    @GetMapping("/igdb-id")
    public ResponseEntity<Platform> findByIGDBId(@RequestParam(name = "id") Long id) {
        try {
            ResponseEntity<Platform> platform = new ResponseEntity<>(serv.findByIgdbID(id), HttpStatus.OK);
            if (platform.getBody() == null) throw new ByIdNotFoundException("igdb-id", id);

            return platform;

        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param name The exact name of the platform
     * @return The corresponding platform
     */
    @GetMapping("/exact-name")
    public ResponseEntity<Platform> findByExactName(@RequestParam(name = "name") String name) {
        try {
            return new ResponseEntity<>(serv.findByName(name), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param name The abbreviation of the platform
     * @return The corresponding platform
     */
    @GetMapping("/abbreviation")
    public ResponseEntity<Platform> findByAbbreviation(@RequestParam(name = "name") String name) {
        try {
            return new ResponseEntity<>(serv.findByAbbreviation(name), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param slug The slug of the platform
     * @return The corresponding platform
     */
    @GetMapping("/platform/{slug}")
    public ResponseEntity<Platform> findBySlug(@PathVariable("slug") String slug) {
        try {
            return new ResponseEntity<>(serv.findBySlug(slug), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param name         platform name
     * @param slug         platform slug
     * @param abbreviation platform abbreviation
     * @param igdbID       platform external API ID
     * @return All platforms that meet the parameters
     */
    @GetMapping("/find")
    public ResponseEntity<Iterable<Platform>> searchPlatforms(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "slug", required = false) String slug,
            @RequestParam(name = "abbreviation", required = false) String abbreviation,
            @RequestParam(name = "igdbID", required = false) Long igdbID
    ) {
        return new ResponseEntity<>(serv.searchPlatforms(name, slug, abbreviation, igdbID), HttpStatus.OK);
    }
}
