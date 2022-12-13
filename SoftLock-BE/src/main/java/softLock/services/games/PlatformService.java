package softLock.services.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import softLock.entities.games.Platform;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.repositories.games.PlatformRepository;

import java.util.Optional;

@Service
public class PlatformService {

    @Autowired
    PlatformRepository rep;

    /**
     * Method to save and persist in db a Platform entity
     *
     * @param platform New platform
     * @return Platform saved in the DB
     */
    public Platform save(Platform platform) {
        return rep.save(platform);
    }

    /**
     * Get All Platforms, return an iterable of Platform
     *
     * @return All platforms in the DB
     */
    public Iterable<Platform> getAllPlatforms() {
        return rep.findAll();
    }

    /**
     * Get All Platforms, return a pageable of platform for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The platforms in the DB paginated
     */
    public Page<Platform> getAllPlatformsPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     *
     * @param id Platform ID
     * @return The corresponding platform
     */
    public Platform findById(Long id) throws ByIdNotFoundException {
        Optional<Platform> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Platform", id);
    }

    /**
     * @param igdbId Platform IGDB ID (the ID of the external DB)
     * @return The corresponding platform
     */
    public Platform findByIgdbID(Long igdbId) {
        Optional<Platform> found = rep.findByIgdbID(igdbId);
        return found.orElse(null);
    }

    /**
     * Find by name, if name is non-existent throws an exception
     *
     * @param name The exact name of the platform
     * @return The corresponding platform
     */
    public Platform findByName(String name) throws ByNameNotFoundException {
        Optional<Platform> found = rep.findByNameAllIgnoreCase(name);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Platform", name);
    }

    /**
     * @param slug The slug of the platform
     * @return The corresponding platform
     */
    public Platform findBySlug(String slug) throws ByNameNotFoundException {
        Optional<Platform> found = rep.findBySlugIgnoreCase(slug);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Game", slug);
    }

    /**
     * @param abbreviation The abbreviation of the platform
     * @return The corresponding platform
     */
    public Platform findByAbbreviation(String abbreviation) throws ByNameNotFoundException {
        Optional<Platform> found = rep.findByAbbreviationIgnoreCase(abbreviation);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Game", abbreviation);
    }

    /**
     * @param name         platform name
     * @param slug         platform slug
     * @param abbreviation platform abbreviation
     * @param igdbID       platform external API ID
     * @return All platforms that meet the parameters
     */
    public Iterable<Platform> searchPlatforms(@Nullable String name, @Nullable String slug, @Nullable String abbreviation, @Nullable Long igdbID) {
        return rep.findByNameOrSlugOrAbbreviationOrIgdbID(name, slug, abbreviation, igdbID);
    }

}
