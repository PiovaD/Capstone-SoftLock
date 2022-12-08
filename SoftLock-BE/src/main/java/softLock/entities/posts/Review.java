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

    public Review(User user, Game game, String title, String text, int vote) {
        super(user, game, title, text);

        this.vote = Math.min(Math.max(vote,0), 10);

    }
}