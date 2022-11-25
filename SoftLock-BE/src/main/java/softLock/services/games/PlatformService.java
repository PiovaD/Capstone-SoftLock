package softLock.services.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     */
    public Platform save(Platform platform) {
        return rep.save(platform);
    }

    /**
     * Get All Platforms, return an iterable of Platform
     */
    public Iterable<Platform> getAllPlatforms() {
        return rep.findAll();
    }

    /**
     * Get All Platforms, return a pageable of platform for lighter payloads
     */
    public Page<Platform> getAllPlatformsPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     */
    public Platform findById(Long id) throws ByIdNotFoundException {
        Optional<Platform> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Platform", id);
    }

    public Platform findByIgdbId(Long igdbId)  {
        Optional<Platform> found = rep.findByIgdbID(igdbId);
        return found.orElse(null);
    }

    /**
     * Find by name, if name is non-existent throws an exception
     */
    public Platform findByName(String name) throws ByNameNotFoundException {
        Optional<Platform> found = rep.findByName(name);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Platform", name);
    }

    /**
     * update Platform
     */
    public Platform updatePlatform(Platform updatedPlatform) throws ByIdNotFoundException {
        rep.save(updatedPlatform);
        return updatedPlatform;
    }

    /**
     * throws IllegalArgumentException
     */
    public String deletePlatform(Long id) {
        rep.deleteById(id);
        return "Platform delete successfully";
    }

}
