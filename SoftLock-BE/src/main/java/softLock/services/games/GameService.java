package softLock.services.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import softLock.entities.games.Game;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.repositories.games.GameRepository;

import java.util.Optional;

@Service
public class GameService {

    @Autowired
    GameRepository rep;

    /**
     * Method to save and persist in db a Game entity
     *
     * @param game new game
     * @return game saved in the DB
     */
    public Game save(Game game) {
        return rep.save(game);
    }

    /**
     * Get All Games, return an iterable of Game
     *
     * @return All Games in the DB
     */
    public Iterable<Game> getAllGames() {
        return rep.findAll();
    }

    /**
     * Get All Games, return a pageable of games for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The games in the DB paginated
     */
    public Page<Game> getAllGamesPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     *
     * @param id Game ID
     * @return The corresponding game
     */
    public Game findById(Long id) throws ByIdNotFoundException {
        Optional<Game> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Game", id);
    }

    /**
     * Find by igdb-id, if id is non-existent throws an exception
     *
     * @param igdbId Game IGDB ID (the ID of the external DB)
     * @return The corresponding game
     */
    public Game findByIgdbID(long igdbId) {
        Optional<Game> found = rep.findByIgdbID(igdbId);
        return found.orElse(null);
    }

    /**
     * Find by name, if name is non-existent throws an exception
     *
     * @param name The exact name of the game
     * @return The corresponding game
     */
    public Game findByName(String name) throws ByNameNotFoundException {
        Optional<Game> found = rep.findByNameAllIgnoreCase(name);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Game", name);
    }

    /**
     * Find by slug, if name is non-existent throws an exception
     *
     * @param slug The slug of the game
     * @return The corresponding game
     */
    public Game findBySlug(String slug) throws ByNameNotFoundException {
        Optional<Game> found = rep.findBySlugIgnoreCase(slug);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Game", slug);
    }

    /**
     * Find all games with properties that match name and platform
     *
     * @param genresName   Genre name
     * @param platformsAbb Platform abbreviation
     * @return All games that meet the parameters
     */
    public Iterable<Game> searchByGenreOrPlatform(@Nullable String genresName, @Nullable String platformsAbb) {
        return rep.findByGenreOrPlatform(genresName, platformsAbb);
    }

    /**
     * update Game
     * !!!need revision!!!
     * @deprecated
     */
    public Game updateGames(Game updatedGame) {
        rep.save(updatedGame);
        return updatedGame;
    }

}
