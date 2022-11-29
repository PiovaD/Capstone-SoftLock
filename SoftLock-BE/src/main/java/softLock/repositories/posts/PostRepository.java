package softLock.repositories.posts;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softLock.entities.posts.Post;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitleSlugIgnoreCase(String titleSlug);

    Set<Post> findByGameId(Long id);

    Set<Post> findByUserId(Long id);

    Set<Post> findByTitleContainsIgnoreCase(String title);

    Set<Post> findByGameNameContainsIgnoreCaseAndTitleContainsIgnoreCase(String name, String title);


}
