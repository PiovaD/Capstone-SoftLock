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
public class Question extends Post {

    public Question(User user, Game game, String title, String text) {
        super(user, game, title, text);
    }

}