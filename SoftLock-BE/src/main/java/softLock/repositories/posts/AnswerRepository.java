package softLock.repositories.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softLock.entities.posts.Answer;

import java.util.Set;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Set<Answer> findByQuestionId(Long id);

    Set<Answer> findByUserId(Long id);

    Set<Answer> findByGameId(Long id);

}
