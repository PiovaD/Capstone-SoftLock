package softLock.controllers;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import softLock.entities.games.Game;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.helper.IgdbHelper;
import softLock.services.games.GameService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:4200/")
public class GameController {

    @Value("${igdb.client-id}")
    private String clientID;

    @Value("${igdb.auth}")
    private String auth;

    @Autowired
    IgdbHelper igdbHelper;

    @Autowired
    GameService serv;

    /*---------------------GET---------------------*/

    /**
     * @return All game in the DB
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Game>> getAllGames() {
        return new ResponseEntity<>(serv.getAllGames(), HttpStatus.OK);
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The games in the DB paginated
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<Game>> getAllGamesPageable(Pageable p) {
        Page<Game> foundAll = serv.getAllGamesPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Game ID
     * @return The corresponding game
     */
    @GetMapping("/id")
    public ResponseEntity<Game> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(serv.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Game IGDB ID (the ID of the external DB)
     * @return The corresponding game
     */
    @GetMapping("/igdb-id")
    public ResponseEntity<Game> findByIGDBId(@RequestParam(name = "id") Long id) {
        try {
            ResponseEntity<Game> game = new ResponseEntity<>(serv.findByIgdbID(id), HttpStatus.OK);
            if (game.getBody() == null) throw new ByIdNotFoundException("igdb-id", id);

            return game;

        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param name The exact name of the game
     * @return The corresponding game
     */
    @GetMapping("/exact-name")
    public ResponseEntity<Game> findByExactName(@RequestParam(name = "name") String name) {
        try {
            return new ResponseEntity<>(serv.findByName(name), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param slug The slug of the game
     * @return The corresponding game
     */
    @GetMapping("/game/{slug}")
    public ResponseEntity<Game> findBySlug(@PathVariable("slug") String slug) {
        try {
            return new ResponseEntity<>(serv.findBySlug(slug), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns a set of games searched in the external API,
     * if it finds a game already present in the db
     * it replaces the one in the set with the one existing in the internal db
     *
     * @param name The name to search for
     * @param size The amount of games
     * @return Set of Games
     */
    @GetMapping("/game-igdb")
    public ResponseEntity<Set<Game>> findInIgdbByName(@RequestParam(name = "name") String name, @RequestParam(name = "size") int size) {
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/games")
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields " +
                        "name, slug, first_release_date, summary, genres, platforms;" +
                        "where category=0;" +
                        "search " + '"' + name + '"' + ";" +
                        "limit " + size + ";")
                .asJson();

        JSONArray res = jsonResponse.getBody().getArray();

        Set<Game> gameList = new HashSet<>();

        for (int i = 0; i < res.length(); i++) {
            Game searchedGame = serv.findByIgdbID(res.getJSONObject(i).getLong("id"));

            gameList.add(
                    searchedGame == null ?
                            igdbHelper.converterGame(res.getJSONObject(i)) :
                            searchedGame
            );
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    /**
     * @param genresName   Genre name
     * @param platformsAbb Platform abbreviation
     * @return All games that meet the parameters
     */
    @GetMapping("/find")
    public ResponseEntity<Iterable<Game>> searchGenresOrPlatform(
            @RequestParam(name = "genresName", required = false) String genresName,
            @RequestParam(name = "platformsAbb", required = false) String platformsAbb
    ) {
        return new ResponseEntity<>(serv.searchByGenreOrPlatform(genresName, platformsAbb), HttpStatus.OK);
    }

    /*---------------------ADDING BY EXTERNAL API---------------------*/

    /**
     *
     * @param igdbId The id of the external API
     * @return The game saved in the internal DB
     */
    @PostMapping("/add/{igdb-id}")
    public ResponseEntity<Game> addByIGDB(@PathVariable("igdb-id") Long igdbId) {
        Game game = serv.findByIgdbID(igdbId);
        if (game == null) {
            HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/games")
                    .header("Client-ID", clientID)
                    .header("Authorization", auth)
                    .header("Accept", "application/json")
                    .body("fields " +
                            "name, slug, first_release_date, summary, genres, platforms;" +
                            "where id = " + igdbId + ";")
                    .asJson();

            JSONArray res = jsonResponse.getBody().getArray();

            return new ResponseEntity<>(igdbHelper.saveGame(res.getJSONObject(0)), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(game, HttpStatus.FOUND);
    }

}
