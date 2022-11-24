package softLock.entities.posts;

import lombok.NoArgsConstructor;
import softLock.entities.games.Game;
import softLock.entities.users.User;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Question extends Post {

    public Question(User user, Game game, String text) {
        super(user, game, text);
    }
}