package softLock.entities.games;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "igdb_id", nullable = false, unique = true)
    private Long igdbID;
    private String name;
    private String slug;
    @Column(columnDefinition="TEXT", length = 510)
    private String summary;
    private String imageID;
    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "game_genres",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private Set<Genre> genres = new java.util.LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "game_platforms",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id")
    )
    private Set<Platform> platforms = new java.util.LinkedHashSet<>();

    @Override
    public String toString() {
        return "Game{" +
                "igdbID=" + igdbID +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", summary='" + summary + '\'' +
                ", imageID='" + imageID + '\'' +
                ", releaseDate=" + releaseDate +
                ", genres=" + genres +
                ", platforms=" + platforms +
                '}';
    }
}