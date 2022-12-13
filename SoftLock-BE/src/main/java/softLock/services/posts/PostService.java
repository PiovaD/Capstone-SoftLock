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

    /**
     * @param post new post
     * @return post saved in the DB
     */
    private Post save(Post post) {
        return rep.save(post);
    }

    /**
     * Get All Posts, return an iterable of Post
     *
     * @return All posts in the DB
     */
    public Iterable<Post> getAllPosts() {
        return rep.findAll();
    }

    /**
     * Get All Posts, return a pageable of post for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The posts in the DB paginated
     */
    public Page<Post> getAllPostsPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     *
     * @param id Post ID
     * @return The corresponding post
     */
    public Post findById(Long id) throws ByIdNotFoundException {
        Optional<Post> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Post", id);
    }

    /**
     * @param titleSlug The slug of the post title
     * @return The corresponding post
     */
    public Post findByTitleSlug(String titleSlug) throws ByNameNotFoundException {
        Optional<Post> post = rep.findByTitleSlugIgnoreCase(titleSlug);
        if (post.isPresent()) {
            return post.get();
        }
        throw new ByNameNotFoundException("Post", titleSlug);
    }

    /**
     * @param gameId The game ID
     * @return All posts that refer to the game passed as a parameter
     */
    public Iterable<Post> findByGameId(Long gameId) {
        return rep.findByGameId(gameId);
    }

    /**
     * @param userId User ID
     * @return All posts that refer to the user passed as a parameter
     */
    public Iterable<Post> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    /**
     * @param title posts title
     * @return All posts that have a similar title
     */
    public Iterable<Post> findByTitle(String title) {
        return rep.findByTitleContainsIgnoreCase(title);
    }

    /**
     * @param gameName Game name
     * @param title    Post title
     * @return All posts that meet the parameters
     */
    public Iterable<Post> searchByGameNameAndTitle(String gameName, String title) {
        return rep.findByGameNameContainsIgnoreCaseAndTitleContainsIgnoreCase(gameName, title);
    }

    /**
     * @param postID Post ID
     * @param userID The User ID of the user who cast the vote
     * @return The entity updated in the DB
     */
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


    /**
     * @param postID Post ID
     * @param userID The User ID of the user who cast the vote
     * @return The entity updated in the DB
     */
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

    /**
     * @param postID Post ID
     * @param userID The User ID of the user who removed the vote
     * @return The entity updated in the DB
     */
    public Post removeVote(Long postID, Long userID) throws ByIdNotFoundException {
        Post post = findById(postID);
        User user = userService.findById(userID);

        post.removeUpVote(user);
        post.removeDownVote(user);

        return rep.save(post);
    }


    /**
     * throws IllegalArgumentException
     *
     * @param post The post to delete
     * @return string with response
     */
    public String deletePost(Post post) throws IllegalArgumentException {
        rep.deleteById(post.getId());
        return "";
    }

}
