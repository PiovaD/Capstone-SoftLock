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
     *
     * @param review New review
     * @return review saved in the DB
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
     *
     * @return All Review in the DB
     */
    public Iterable<Review> getAllReviews() {
        return rep.findAll();
    }

    /**
     * Get All Reviews, return a pageable of review for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The Reviews in the DB paginated
     */
    public Page<Review> getAllReviewsPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * @param id Review ID
     * @return The corresponding Review
     */
    public Review findById(Long id) throws ByIdNotFoundException {
        Optional<Review> res = rep.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ByIdNotFoundException("Review", id);
    }

    /**
     * @param gameId The game ID
     * @return All reviews that refer to the game passed as a parameter
     */
    public Iterable<Integer> getReviewsVoteByGameId(Long gameId) {
        return rep.getReviewsVoteByGameId(gameId);
    }

    /**
     * @param userId User ID
     * @return All Reviews that refer to the user passed as a parameter
     */
    public Iterable<Review> findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    /**
     * @param gameId The game ID
     * @return All Reviews that refer to the game passed as a parameter
     */
    public Iterable<Review> findByGameId(Long gameId) {
        return rep.findByGameId(gameId);
    }

    /**
     * @param title The title of the Review
     * @return All Reviews that have a similar title
     */
    public Iterable<Review> finByTitle(String title) {
        return rep.findByTitle(title);
    }

    /**
     * @param updatedReview Updated review
     * @return The entity updated in the DB
     */
    public Review update(Review updatedReview) throws ByIdNotFoundException {
        Review newRev = findById(updatedReview.getId());

        newRev.setGame(gameService.findById(updatedReview.getGame().getId()));
        newRev.setTitle(updatedReview.getTitle());
        newRev.setTitleSlug(updatedReview.getTitle().toLowerCase().replaceAll("\\s", "-"));
        newRev.setText(updatedReview.getText());
        newRev.setVote(updatedReview.getVote());

        return rep.save(newRev);
    }

}
