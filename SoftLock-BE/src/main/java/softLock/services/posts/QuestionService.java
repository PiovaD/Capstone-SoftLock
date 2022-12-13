package softLock.services.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
     *
     * @param question New question
     * @return question saved in the DB
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
     *
     * @return All questions in the DB
     */
    public Iterable<Question> getAllQuestions() {
        return rep.findAll();
    }

    /**
     * Get All Questions, return a pageable of question for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The questions in the DB paginated
     */
    public Page<Question> getAllQuestionsPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * @param userId User ID
     * @return All questions that refer to the user passed as a parameter
     */
    public Iterable<Question> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }


    /**
     * @param gameId The game ID
     * @return All questions that refer to the game passed as a parameter
     */
    public Iterable<Question> findByGameId(Long gameId) {
        return rep.findByGameId(gameId);
    }

    /**
     * @param id question ID
     * @return The corresponding question
     */
    public Question findById(Long id) throws ByIdNotFoundException {
        Optional<Question> res = rep.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ByIdNotFoundException("Question", id);
    }

    /**
     * @param title The title of the question
     * @return All questions that have a similar title
     */
    public Iterable<Question> finByTitle(String title) {
        return rep.findByTitle(title);
    }

    /**
     * @param updatedQuestion Updated question
     * @return The entity updated in the DB
     */
    public Question update(Question updatedQuestion) throws ByIdNotFoundException {
        Question newQuestion = findById(updatedQuestion.getId());

        newQuestion.setGame(gameService.findById(updatedQuestion.getGame().getId()));
        newQuestion.setTitle(updatedQuestion.getTitle());
        newQuestion.setTitleSlug(updatedQuestion.getTitle().toLowerCase().replaceAll("\\s", "-"));
        newQuestion.setText(updatedQuestion.getText());

        return rep.save(newQuestion);
    }

}
