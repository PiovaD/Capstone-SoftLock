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
     */
    public Iterable<Answer> getAllAnswers() {
        return rep.findAll();
    }

    /**
     * Get All Answers, return a pageable of answer for lighter payloads
     */
    public Page<Answer> getAllAnswersPageable(Pageable p) {
        return rep.findAll(p);
    }


    public Iterable<Answer> findByQuestion(Long questionId) {

        return rep.findByQuestionId(questionId);
    }

    public Iterable<Answer> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    private Answer findById(Long id) throws ByIdNotFoundException {
        Optional<Answer> res = rep.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ByIdNotFoundException("Answer", id);
    }

    public Answer update(Answer updateReview) throws ByIdNotFoundException {
        Answer newRev = findById(updateReview.getId());

        newRev.setTitle(null);
        newRev.setText(updateReview.getText());

        return rep.save(newRev);
    }
}
