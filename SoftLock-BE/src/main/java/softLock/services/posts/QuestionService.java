package softLock.services.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softLock.entities.posts.Post;
import softLock.entities.posts.Question;
import softLock.exceptions.ByIdNotFoundException;
import softLock.repositories.posts.QuestionRepository;
import softLock.services.games.GameService;
import softLock.services.users.UserService;

import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository rep;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    PostService postService;

    /**
     * Method to save and persist in db a Post entity
     */
    public Question save(Question question) throws ByIdNotFoundException {

        Question newQuestion = new Question(
                userService.findById(question.getUser().getId()),
                gameService.findById(question.getGame().getId()),
                question.getTitle(),
                question.getText()
        );

        return rep.save(newQuestion);
    }

    /**
     * Get All Questions, return an iterable of Question
     */
    public Iterable<Question> getAllQuestions() {
        return rep.findAll();
    }

    /**
     * Get All Questions, return a pageable of question for lighter payloads
     */
    public Page<Question> getAllQuestionsPageable(Pageable p) {
        return rep.findAll(p);
    }


    public Iterable<Question> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    public Iterable<Question> findByGameId(Long gameId) {
        return rep.findByGameId(gameId);
    }

    private Question findById(Long id) throws ByIdNotFoundException {
        Optional<Question> res = rep.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ByIdNotFoundException("Question", id);
    }

    public Question update(Question updateQuestion) throws ByIdNotFoundException {
        Question newQuestion = findById(updateQuestion.getId());

        newQuestion.setGame(gameService.findById(updateQuestion.getGame().getId()));
        newQuestion.setTitle(updateQuestion.getTitle());
        newQuestion.setTitleSlug(updateQuestion.getTitle().toLowerCase().replaceAll("\\s", "-"));
        newQuestion.setText(updateQuestion.getText());

        return rep.save(newQuestion);
    }

}
