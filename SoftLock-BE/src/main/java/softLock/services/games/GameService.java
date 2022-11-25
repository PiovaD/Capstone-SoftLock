package softLock.services.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     */
    public Game save(Game game) {
        return rep.save(game);
    }

    /**
     * Get All Games, return an iterable of Game
     */
    public Iterable<Game> getAllGames() {
        return rep.findAll();
    }

    /**
     * Get All Games, return a pageable of games for lighter payloads
     */
    public Page<Game> getAllGamesPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     */
    public Game findById(Long id) throws ByIdNotFoundException {
        Optional<Game> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Game", id);
    }

    /**
     * Find by name, if name is non-existent throws an exception
     */
    public Game findByName(String name) throws ByNameNotFoundException {
        Optional<Game> found = rep.findByName(name);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("Game", name);
    }

    /**
     * update Game
     */
    public Game updateGames(Game updatedGame) throws ByIdNotFoundException {
        rep.save(updatedGame);
        return updatedGame;
    }

    /**
     * throws IllegalArgumentException
     */
    public String deleteGames(Long id) {
        rep.deleteById(id);
        return "Games delete successfully";
    }


    public Game findByIgdbId(long igdbId) {
        Optional<Game> found = rep.findByIgdbID(igdbId);
        return found.orElse(null);
    }
}