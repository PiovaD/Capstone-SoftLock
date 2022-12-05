package softLock.repositories.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softLock.entities.posts.Review;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Set<Review> findByUserId(Long id);

    @Query("select r.vote from Review r where r.game.id = ?1")
    Iterable<Integer> getReviewsByGameId(Long id);

}
