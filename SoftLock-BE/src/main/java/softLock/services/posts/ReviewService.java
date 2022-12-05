package softLock.services.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softLock.entities.posts.Review;
import softLock.exceptions.ByIdNotFoundException;
import softLock.repositories.posts.ReviewRepository;
import softLock.services.games.GameService;
import softLock.services.users.UserService;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository rep;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    PostService postService;

    /**
     * Method to save and persist in db a Post entity
     */
    public Review save(Review review) throws ByIdNotFoundException {

        Review newReview = new Review(
                userService.findById(review.getUser().getId()),
                gameService.findById(review.getGame().getId()),
                review.getTitle(),
                review.getText(),
                review.getVote()
        );

        return rep.save(newReview);

    }

    /**
     * Get All Reviews, return an iterable of Review
     */
    public Iterable<Review> getAllReviews() {
        return rep.findAll();
    }

    /**
     * Get All Reviews, return a pageable of review for lighter payloads
     */
    public Page<Review> getAllReviewsPageable(Pageable p) {
        return rep.findAll(p);
    }

    private Review findById(Long id) throws ByIdNotFoundException {
        Optional<Review> res = rep.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ByIdNotFoundException("Review", id);
    }

    public Iterable<Review> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    public Review update(Review updateReview) throws ByIdNotFoundException {
        Review newRev = findById(updateReview.getId());

        newRev.setGame(gameService.findById(updateReview.getGame().getId()));
        newRev.setTitle(updateReview.getTitle());
        newRev.setTitleSlug(updateReview.getTitle().toLowerCase().replaceAll("\\s", "-"));
        newRev.setText(updateReview.getText());
        newRev.setVote(updateReview.getVote());

        return rep.save(newRev);
    }

}
