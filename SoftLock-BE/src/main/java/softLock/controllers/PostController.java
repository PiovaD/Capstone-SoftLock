package softLock.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import softLock.entities.posts.Answer;
import softLock.entities.posts.Post;
import softLock.entities.posts.Question;
import softLock.entities.posts.Review;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.services.posts.AnswerService;
import softLock.services.posts.PostService;
import softLock.services.posts.QuestionService;
import softLock.services.posts.ReviewService;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200/")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    ReviewService reviewService;

    /**
     * @return All posts in the DB
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    /**
     * @return All questions in the DB
     */
    @GetMapping("/questions")
    public ResponseEntity<Iterable<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    /**
     * @return All answers in the DB
     */
    @GetMapping("/answers")
    public ResponseEntity<Iterable<Answer>> getAllAnswers() {
        return new ResponseEntity<>(answerService.getAllAnswers(), HttpStatus.OK);
    }

    /**
     * @return All reviews in the DB
     */
    @GetMapping("/reviews")
    public ResponseEntity<Iterable<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The posts in the DB paginated
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<Post>> getAllPostsPageable(Pageable p) {
        Page<Post> foundAll = postService.getAllPostsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The questions in the DB paginated
     */
    @GetMapping("/questions-pageable")
    public ResponseEntity<Page<Question>> getAllQuestionsPageable(Pageable p) {
        Page<Question> foundAll = questionService.getAllQuestionsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The answers in the DB paginated
     */
    @GetMapping("/answers-pageable")
    public ResponseEntity<Page<Answer>> getAllAnswerPageable(Pageable p) {
        Page<Answer> foundAll = answerService.getAllAnswersPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The reviews in the DB paginated
     */
    @GetMapping("/reviews-pageable")
    public ResponseEntity<Page<Review>> getAllReviewPageable(Pageable p) {
        Page<Review> foundAll = reviewService.getAllReviewsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id Post ID
     * @return The corresponding post
     */
    @GetMapping("/id")
    public ResponseEntity<Post> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param titleSlug The slug of the post title
     * @return The corresponding post
     */
    @GetMapping("/title/{title-slug}")
    public ResponseEntity<Post> findByTitleSlug(@PathVariable("title-slug") String titleSlug) {
        try {
            return new ResponseEntity<>(postService.findByTitleSlug(titleSlug), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param gameId The game ID
     * @return All posts that refer to the game passed as a parameter
     */
    @GetMapping("/game-id")
    public ResponseEntity<Iterable<Post>> findByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(postService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param gameId The game ID
     * @return All questions that refer to the game passed as a parameter
     */
    @GetMapping("questions/game-id")
    public ResponseEntity<Iterable<Question>> findQuestionsByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(questionService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param gameId The game ID
     * @return All answers that refer to the game passed as a parameter
     */
    @GetMapping("answers/game-id")
    public ResponseEntity<Iterable<Answer>> findAnswersByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(answerService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param gameId The game ID
     * @return All reviews that refer to the game passed as a parameter
     */
    @GetMapping("reviews/game-id")
    public ResponseEntity<Iterable<Review>> findReviewsByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(reviewService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId User ID
     * @return All posts that refer to the user passed as a parameter
     */
    @GetMapping("/user-id")
    public ResponseEntity<Iterable<Post>> findByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(postService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param title The title of the post
     * @return All posts that have a similar title
     */
    @GetMapping("/title")
    public ResponseEntity<Iterable<Post>> findByTitle(@RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(postService.findByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param gameName Game name
     * @param title    Post title
     * @return All posts that meet the parameters
     */
    @GetMapping("/search")
    public ResponseEntity<Iterable<Post>> searchByGameNameAndTitle(@RequestParam(name = "game-name") String gameName, @RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(postService.searchByGameNameAndTitle(gameName, title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param title question title
     * @return All questions that have a similar title
     */
    @GetMapping("/search/question-title")
    public ResponseEntity<Iterable<Question>> searchByQuestionTitle(@RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(questionService.finByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param title reviews title
     * @return All reviews that have a similar title
     */
    @GetMapping("/search/review-title")
    public ResponseEntity<Iterable<Review>> searchByReviewTitle(@RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(reviewService.finByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId User ID
     * @return All questions with the same user id
     */
    @GetMapping("/questions/user-id")
    public ResponseEntity<Iterable<Question>> findQuestionByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(questionService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId User ID
     * @return All answers with the same user id
     */
    @GetMapping("/answers/user-id")
    public ResponseEntity<Iterable<Answer>> findAnswerByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(answerService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId User ID
     * @return All reviews with the same user id
     */
    @GetMapping("/reviews/user-id")
    public ResponseEntity<Iterable<Review>> findReviewByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(reviewService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param gameId The game ID
     * @return The review rating
     */
    @GetMapping("/reviews-vote/game-id")
    public ResponseEntity<Iterable<Integer>> getReviewsByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(reviewService.getReviewsVoteByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param questionId Question ID
     * @return All the answers to the question
     */
    @GetMapping("/answers/question")
    public ResponseEntity<Iterable<Answer>> findAnswerByQuestionId(@RequestParam(name = "question-id") Long questionId) {
        try {
            return new ResponseEntity<>(answerService.findByQuestion(questionId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*---------------------POST---------------------*/

    /**
     * @param post New post to add
     * @return The entity saved in the DB
     */
    @PostMapping("/new-question")
    public ResponseEntity<Question> createNewQuestion(@RequestBody Question post) {
        try {
            return new ResponseEntity<>(questionService.save(post), HttpStatus.OK);
        } catch (Exception | ByIdNotFoundException e) {
            log.error("Error saving post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param post New answer to add
     * @return The entity saved in the DB
     */
    @PostMapping("/new-answer")
    public ResponseEntity<Answer> createNewAnswer(@RequestBody Answer post) {
        try {
            return new ResponseEntity<>(answerService.save(post), HttpStatus.OK);
        } catch (Exception | ByIdNotFoundException e) {
            log.error("Error saving post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param post New review to add
     * @return The entity saved in the DB
     */
    @PostMapping("/new-review")
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN', 'GAMER', 'PRESS', 'INFLUENCER' )")
    public ResponseEntity<Review> createNewReview(@RequestBody Review post) {
        try {
            return new ResponseEntity<>(reviewService.save(post), HttpStatus.OK);
        } catch (Exception | ByIdNotFoundException e) {
            log.error("Error saving post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*---------------------PUT---------------------*/

    /**
     * @param updatedAnswer Updated Answer
     * @return The entity updated in the DB
     */
    @PutMapping("/update-answer")
    public ResponseEntity<Answer> updateAnswer(@RequestBody Answer updatedAnswer) {
        try {
            return new ResponseEntity<>(answerService.update(updatedAnswer), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param updatedQuestion Updated question
     * @return The entity updated in the DB
     */
    @PutMapping("/update-question")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question updatedQuestion) {
        try {
            return new ResponseEntity<>(questionService.update(updatedQuestion), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param updatedReview Updated review
     * @return The entity updated in the DB
     */
    @PutMapping("/update-review")
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN', 'GAMER', 'PRESS', 'INFLUENCER' )")
    public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview) {
        try {
            return new ResponseEntity<>(reviewService.update(updatedReview), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param postID Post ID
     * @param userID The User ID of the user who cast the vote
     * @return The entity updated in the DB
     */
    @PutMapping("/up-vote/add/{post-id}/{user-id}")
    public ResponseEntity<Post> addUpVote(@PathVariable("post-id") Long postID, @PathVariable("user-id") Long userID) {
        try {
            return new ResponseEntity<>(postService.addUpVote(postID, userID), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param postID Post ID
     * @param userID The User ID of the user who cast the vote
     * @return The entity updated in the DB
     */
    @PutMapping("/down-vote/add/{post-id}/{user-id}")
    public ResponseEntity<Post> addDownVote(@PathVariable("post-id") Long postID, @PathVariable("user-id") Long userID) {
        try {
            return new ResponseEntity<>(postService.addDownVote(postID, userID), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    /*---------------------DELETE---------------------*/

    /**
     * @param postID Post ID
     * @param userID The User ID of the user who removed the vote
     * @return The entity updated in the DB
     */
    @DeleteMapping("/vote/remove/{post-id}/{user-id}")
    public ResponseEntity<Post> removeUpVote(@PathVariable("post-id") Long postID, @PathVariable("user-id") Long userID) {
        try {
            return new ResponseEntity<>(postService.removeVote(postID, userID), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param post The post to delete
     * @return string with response
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Post post) {
        try {
            return new ResponseEntity<>(postService.deletePost(post), HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            log.error("Error deleting post " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            log.error("Error deleting post (id could be null): " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
