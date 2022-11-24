package SoftLock.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
public abstract class Post {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_ID")
    private Game game;

    private String text;

    @ManyToMany
    @JoinTable(name = "posts_up_vote",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "up_vote_id"))
    private Set<User> upVote = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "posts_down_vote",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "down_vote_id"))
    private Set<User> downVote = new HashSet<>();

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", game=" + game +
                ", text='" + text + '\'' +
                '}';
    }
}
