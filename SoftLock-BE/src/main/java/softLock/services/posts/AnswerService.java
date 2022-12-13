package softLock.services.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import softLock.entities.posts.Answer;
import softLock.entities.posts.Question;
import softLock.exceptions.ByIdNotFoundException;
import softLock.repositories.posts.AnswerRepository;
import softLock.services.games.GameService;
import softLock.services.users.UserService;

import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository rep;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    PostService postService;

    /**
     * Method to save and persist in db a Post entity
     *
     * @param answer new answer
     * @return answer saved in the DB
     */
    public Answer save(Answer answer) throws ByIdNotFoundException {

        Question q = (Question) postService.findById(answer.getQuestion().getId());

        Answer newAnswer = new Answer(
                q,
                userService.findById(answer.getUser().getId()),
                gameService.findById(q.getGame().getId()),
                answer.getText()
        );

        return rep.save(newAnswer);
    }

    /**
     * Get All Answers, return an iterable of Answer
     *
     * @return All answers in the DB
     */
    public Iterable<Answer> getAllAnswers() {
        return rep.findAll();
    }

    /**
     * Get All Answers, return a pageable of answer for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The answers in the DB paginated
     */
    public Page<Answer> getAllAnswersPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * @param questionId Question ID
     * @return All the answers to the question
     */
    public Iterable<Answer> findByQuestion(Long questionId) {
        return rep.findByQuestionId(questionId);
    }

    /**
     * @param userId User ID
     * @return All answers with the same user id
     */
    public Iterable<Answer> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    /**
     * @param gameId The game ID
     * @return All answer that refer to the game passed as a parameter
     */
    public Iterable<Answer> findByGameId(Long gameId) {
        return rep.findByGameId(gameId);
    }

    /**
     * @param id Post ID
     * @return The corresponding answer
     */
    private Answer findById(Long id) throws ByIdNotFoundException {
        Optional<Answer> res = rep.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ByIdNotFoundException("Answer", id);
    }

    /**
     * @param updatedAnswer Updated Answer
     * @return The entity updated in the DB
     */
    public Answer update(Answer updatedAnswer) throws ByIdNotFoundException {
        Answer newRev = findById(updatedAnswer.getId());

        newRev.setTitle(null);
        newRev.setText(updatedAnswer.getText());

        return rep.save(newRev);
    }
}
