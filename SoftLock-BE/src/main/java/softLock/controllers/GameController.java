package softLock.controllers;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
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

    @GetMapping("")
    public ResponseEntity<Iterable<Game>> getAllGames() {
        return new ResponseEntity<>(serv.getAllGames(), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Game>> getAllGamesPageable(Pageable p) {
        Page<Game> foundAll = serv.getAllGamesPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<Game> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(serv.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

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

    @GetMapping("/exact-name")
    public ResponseEntity<Game> findByExactName(@RequestParam(name = "name") String name) {
        try {
            return new ResponseEntity<>(serv.findByName(name), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/game/{slug}")
    public ResponseEntity<Game> findBySlug(@PathVariable("slug") String slug) {
        try {
            return new ResponseEntity<>(serv.findBySlug(slug), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/game-igdb")
    public ResponseEntity<Set<Game>> findInIgdbByName(@RequestParam(name = "name") String name, @RequestParam(name = "size") int size) {
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/games")
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields " +
                        "name, slug, first_release_date, summary, genres, platforms;" +
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

    /*---------------------ADDING BY EXTERNAL API---------------------*/

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