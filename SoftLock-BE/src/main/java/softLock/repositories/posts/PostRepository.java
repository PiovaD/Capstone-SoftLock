package softLock.repositories.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softLock.entities.posts.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
