package softLock.entities.posts;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import softLock.entities.games.Game;
import softLock.entities.users.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Post {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Instant date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_ID")
    private Game game;


    @Column(unique = true)
    private String title;
    private String titleSlug;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToMany
    @JoinTable(name = "posts_up_vote",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> upVote = new java.util.LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "posts_down_vote",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> downVote = new java.util.LinkedHashSet<>();

    public Post(User user, Game game, String title, String text) {
        this.date = Instant.now();
        this.user = user;
        this.game = game;
        this.title = title;
        this.titleSlug = title != null ? title.toLowerCase().replaceAll("\\s", "-") : null;
        this.text = text;
    }

    public void addUpVote(User user) {
        this.upVote.add(user);
    }

    public void addDownVote(User user) {
        this.downVote.add(user);
    }

    public void removeUpVote(User user) {
        this.upVote.remove(user);
    }

    public void removeDownVote(User user) {
        this.downVote.remove(user);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", game=" + game +
                ", title='" + title + '\'' +
                ", titleSlug='" + titleSlug + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
