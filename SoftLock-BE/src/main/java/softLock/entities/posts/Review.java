package softLock.entities.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softLock.entities.games.Game;
import softLock.entities.users.User;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends Post {
    private int vote;

    public Review(User user, Game game, String text, int vote) {
        super(user, game, text);
        this.vote = vote;
    }
}