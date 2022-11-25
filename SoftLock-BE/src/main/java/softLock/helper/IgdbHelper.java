package softLock.helper;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import softLock.entities.games.Game;
import softLock.entities.games.Genre;
import softLock.entities.games.Platform;
import softLock.services.games.GameService;
import softLock.services.games.GenreService;
import softLock.services.games.PlatformService;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Component
public class IgdbHelper {

    @Value("${igdb.client-id}")
    private String clientID;

    @Value("${igdb.auth}")
    private String auth;

    @Autowired
    PlatformService platformService;

    @Autowired
    GenreService genreService;

    @Autowired
    GameService gameService;

    private final long actualUnixTime = System.currentTimeMillis() / 1000L;

    public void gamesFiller() {

        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/games")
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields " +
                        "name, slug, first_release_date, summary, genres, platforms;" +
                        "where " +
                        "first_release_date <=" + actualUnixTime + " " +
                        "& (hypes > 20 | total_rating > 65 & hypes > 15)" +
                        "& total_rating != null " +
                        "& hypes != null;" +
                        "sort first_release_date desc;")
                .asJson();

        JSONArray res = jsonResponse.getBody().getArray();

        for (int i = 0; i < res.length(); i++) {
            System.out.println(saveGame(res.getJSONObject(i)));
        }

    }

    public Game saveGame(JSONObject game) {
        if (gameService.findByIgdbId(game.getLong("id")) == null) {
            Game newGame = new Game();
            newGame.setIgdbID(game.getLong("id"));
            newGame.setName(game.getString("name"));
            newGame.setSlug(game.getString("slug"));
            newGame.setSummary(game.getString("summary"));
            newGame.setReleaseDate(Instant.ofEpochSecond(game.getLong("first_release_date")).atZone(ZoneId.systemDefault()).toLocalDate());
            newGame.setImageID(getImg(newGame.getIgdbID(), "game", "covers"));

            newGame.setPlatforms(savePlatform(game.getJSONArray("platforms")));
            newGame.setGenres(saveGenre(game.getJSONArray("genres")));

            return gameService.save(newGame);
        } else {
            return gameService.findByIgdbId(game.getLong("id"));
        }
    }

    public Set<Platform> savePlatform(JSONArray platforms) {
        Set<Platform> res = new HashSet<>();
        for (int i = 0; i < platforms.length(); i++) {
            Platform p = platformService.findByIgdbId(platforms.getLong(i));
            if (p == null) {
                res.add(newPlatform(platforms.getLong(i)));
            } else {
                res.add(p);
            }
        }
        return res;
    }

    public Set<Genre> saveGenre(JSONArray genres) {
        Set<Genre> res = new HashSet<>();
        for (int i = 0; i < genres.length(); i++) {
            Genre g = genreService.findByIgdbId(genres.getLong(i));
            if (g == null) {
                res.add(newGenre(genres.getLong(i)));
            } else {
                res.add(g);
            }
        }
        return res;
    }

    private Genre newGenre(long id) {
        Genre genre = new Genre();

        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/genres")
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields name, slug; where id =" + id + ";")
                .asJson();

        JSONObject rawPlatform = jsonResponse.getBody().getArray().getJSONObject(0);
        genre.setIgdbID(rawPlatform.getLong("id"));
        genre.setName(rawPlatform.getString("name"));
        genre.setSlug(rawPlatform.getString("slug"));

        return genreService.save(genre);
    }

    public Platform newPlatform(long id) {
        Platform platform = new Platform();

        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/platforms")
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields name, slug, abbreviation, platform_logo; where id =" + id + ";")
                .asJson();

        JSONObject rawPlatform = jsonResponse.getBody().getArray().getJSONObject(0);
        platform.setIgdbID(rawPlatform.getLong("id"));
        platform.setName(rawPlatform.getString("name"));
        platform.setSlug(rawPlatform.getString("slug"));
        platform.setAbbreviation(rawPlatform.getString("abbreviation"));
        platform.setPlatformImageId(getImg(rawPlatform.getLong("platform_logo"), "id", "platform_logos"));

        return platformService.save(platform);
    }

    public String getImg(Long id, String where, String url) {
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/" + url)
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields image_id;" +
                        "where " + where + " =" + id + ";"
                )
                .asJson();

        return jsonResponse.getBody().getArray().getJSONObject(0).getString("image_id");
    }
}
