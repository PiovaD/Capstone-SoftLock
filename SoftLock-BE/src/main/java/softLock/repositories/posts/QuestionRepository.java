package softLock.repositories.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softLock.entities.posts.Question;

import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Set<Question> findByUserId(Long id);

    Set<Question> findByGameId(Long id);

    @Query("select q from Question q where upper(q.title) like upper(concat('%', ?1, '%'))")
    Set<Question> findByTitle(String title);


}
