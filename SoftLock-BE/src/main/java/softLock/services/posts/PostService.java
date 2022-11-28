package softLock.services.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softLock.entities.posts.Post;
import softLock.exceptions.ByIdNotFoundException;
import softLock.repositories.posts.PostRepository;

import java.util.Optional;

@Service
public class PostService {

         @Autowired
         PostRepository rep;

    /**
     * Method to save and persist in db a Post entity
     */
    public Post save(Post genre) {
        return rep.save(genre);
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

    /**
     * update Post
     */
    public Post updatePlatform(Post updatedPost){
        rep.save(updatedPost);
        return updatedPost;
    }

    /**
     * throws IllegalArgumentException
     */
    public String deletePost(Long id) {
        rep.deleteById(id);
        return "Post delete successfully";
    }
}
