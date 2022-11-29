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

    /**
     * Retrieve games from the external api in order of release and for a given hype and rating
     */
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
            saveGame(res.getJSONObject(i));
        }

    }

    /**
     * check if the game is already present in the db using the id of the external api, if it is not present it creates and adds it, otherwise it simply returns it
     *
     * @param game the game taken from the external API
     * @return the saved game
     */
    public Game saveGame(JSONObject game) {
        if (gameService.findByIgdbID(game.getLong("id")) == null) {
            return gameService.save(converterGame(game));

        } else {
            return gameService.findByIgdbID(game.getLong("id"));
        }
    }

    /**
     * Convert Json Obj to Game obj
     *
     * @param game Json obj
     * @return the converted game
     */
    public Game converterGame(JSONObject game) {
        Game newGame = new Game();
        newGame.setIgdbID(game.getLong("id"));
        newGame.setName(game.getString("name"));
        newGame.setSlug(game.has("slug") ? game.getString("slug") : null);
        newGame.setSummary(game.has("summary") ? game.getString("summary") : null);
        newGame.setReleaseDate(game.has("first_release_date") ? Instant.ofEpochSecond(game.getLong("first_release_date")).atZone(ZoneId.systemDefault()).toLocalDate() : null);
        newGame.setImageID(getImg(newGame.getIgdbID(), "game", "covers"));

        newGame.setPlatforms(game.has("platforms") ? savePlatform(game.getJSONArray("platforms")) : null);
        newGame.setGenres(game.has("genres") ? saveGenre(game.getJSONArray("genres")) : null);

        return newGame;
    }

    /**
     * check if the platform is already present in the db using the id of the external api, if it is not present it creates and adds it, otherwise it simply returns it
     *
     * @param platforms the platforms array taken from the external API
     * @return the saved platform
     */
    public Set<Platform> savePlatform(JSONArray platforms) {
        Set<Platform> res = new HashSet<>();
        for (int i = 0; i < platforms.length(); i++) {
            Platform p = platformService.findByIgdbID(platforms.getLong(i));
            if (p == null) {
                res.add(newPlatform(platforms.getLong(i)));
            } else {
                res.add(p);
            }
        }
        return res;
    }

    /**
     * check if the genre is already present in the db using the id of the external api, if it is not present it creates and adds it, otherwise it simply returns it
     *
     * @param genres the genres array taken from the external API
     * @return the saved genre
     */
    public Set<Genre> saveGenre(JSONArray genres) {
        Set<Genre> res = new HashSet<>();
        for (int i = 0; i < genres.length(); i++) {
            Genre g = genreService.findByIgdbID(genres.getLong(i));
            if (g == null) {
                res.add(newGenre(genres.getLong(i)));
            } else {
                res.add(g);
            }
        }
        return res;
    }

    /**
     * @param id the id to find the item in the external api
     * @return new genre
     */
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

    /**
     * @param id the id to find the item in the external api
     * @return new platform
     */
    private Platform newPlatform(long id) {
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

    /**
     * finds the id of the image and returns it
     *
     * @param id    the id to search for
     * @param where the parameter to search corresponding to the id
     * @param url   the url of the external api <a href="https://api.igdb.com/v4/">https://api.igdb.com/v4/</a> + url
     * @return the image id
     */
    public String getImg(Long id, String where, String url) {
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/" + url)
                .header("Client-ID", clientID)
                .header("Authorization", auth)
                .header("Accept", "application/json")
                .body("fields image_id;" +
                        "where " + where + " =" + id + ";"
                )
                .asJson();

        return jsonResponse.getBody().getArray().length() > 0 ? jsonResponse.getBody().getArray().getJSONObject(0).getString("image_id") : null;
    }
}
