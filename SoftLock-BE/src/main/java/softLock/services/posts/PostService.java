package softLock.services.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softLock.entities.posts.Post;
import softLock.entities.users.User;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.repositories.posts.PostRepository;
import softLock.services.users.UserService;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository rep;

    @Autowired
    UserService userService;

    private Post save(Post post) {
        return rep.save(post);
    }

    /**
     * Get All Posts, return an iterable of Post
     */
    public Iterable<Post> getAllPosts() {
        return rep.findAll();
    }

    /**
     * Get All Posts, return a pageable of post for lighter payloads
     */
    public Page<Post> getAllPostsPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     */
    public Post findById(Long id) throws ByIdNotFoundException {
        Optional<Post> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Post", id);
    }

    public Post findByTitleSlug(String titleSlug) throws ByNameNotFoundException {
        Optional<Post> post = rep.findByTitleSlugIgnoreCase(titleSlug);
        if (post.isPresent()) {
            return post.get();
        }
        throw new ByNameNotFoundException("Post", titleSlug);
    }

    public Iterable<Post> findByGameId(Long gameId) {
        return rep.findByGameId(gameId);
    }

    public Iterable<Post> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    public Iterable<Post> findByTitle(String title) {
        return rep.findByTitleContainsIgnoreCase(title);
    }

    public Iterable<Post> searchByGameNameAndTitle(String gameName, String title) {
        return rep.findByGameNameContainsIgnoreCaseAndTitleContainsIgnoreCase(gameName, title);
    }

    //todo up down vote

    public Post addUpVote(Long postID, Long userID) throws ByIdNotFoundException {
        Post post = findById(postID);
        User user = userService.findById(userID);

        if (!post.getUpVote().contains(user)) {

            if (post.getDownVote().contains(user)) {
                post.removeDownVote(user);
            }
            post.addUpVote(user);
        }

        return rep.save(post);
    }

    public Post addDownVote(Long postID, Long userID) throws ByIdNotFoundException {
        Post post = findById(postID);
        User user = userService.findById(userID);

        if (!post.getDownVote().contains(user)) {

            if (post.getUpVote().contains(user)) {
                post.removeUpVote(user);
            }
            post.addDownVote(user);
        }

        return rep.save(post);
    }

    public Post removeVote(Long postID, Long userID) throws ByIdNotFoundException {
        Post post = findById(postID);
        User user = userService.findById(userID);

        post.removeUpVote(user);
        post.removeDownVote(user);

        return rep.save(post);
    }


    /**
     * throws IllegalArgumentException
     */
    public String deletePost(Post post) throws IllegalArgumentException {
        rep.deleteById(post.getId());
        return "Post delete successfully";
    }

}
