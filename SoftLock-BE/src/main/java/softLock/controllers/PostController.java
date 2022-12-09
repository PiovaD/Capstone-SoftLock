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

    @GetMapping("")
    public ResponseEntity<Iterable<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<Iterable<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/answers")
    public ResponseEntity<Iterable<Answer>> getAllAnswers() {
        return new ResponseEntity<>(answerService.getAllAnswers(), HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public ResponseEntity<Iterable<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Post>> getAllPostsPageable(Pageable p) {
        Page<Post> foundAll = postService.getAllPostsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/questions-pageable")
    public ResponseEntity<Page<Question>> getAllQuestionsPageable(Pageable p) {
        Page<Question> foundAll = questionService.getAllQuestionsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/answers-pageable")
    public ResponseEntity<Page<Answer>> getAllAnswerPageable(Pageable p) {
        Page<Answer> foundAll = answerService.getAllAnswersPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reviews-pageable")
    public ResponseEntity<Page<Review>> getAllReviewPageable(Pageable p) {
        Page<Review> foundAll = reviewService.getAllReviewsPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<Post> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/title/{title-slug}")
    public ResponseEntity<Post> findByTitleSlug(@PathVariable("title-slug") String titleSlug) {
        try {
            return new ResponseEntity<>(postService.findByTitleSlug(titleSlug), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/game-id")
    public ResponseEntity<Iterable<Post>> findByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(postService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("questions/game-id")
    public ResponseEntity<Iterable<Question>> findQuestionsByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(questionService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("answers/game-id")
    public ResponseEntity<Iterable<Answer>> findAnswersByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(answerService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("reviews/game-id")
    public ResponseEntity<Iterable<Review>> findReviewsByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(reviewService.findByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/user-id")
    public ResponseEntity<Iterable<Post>> findByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(postService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/title")
    public ResponseEntity<Iterable<Post>> findByTitle(@RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(postService.findByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Post>> searchByGameNameAndTitle(@RequestParam(name = "game-name") String gameName, @RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(postService.searchByGameNameAndTitle(gameName, title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/question-title")
    public ResponseEntity<Iterable<Question>> searchByQuestionTitle(@RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(questionService.finByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/review-title")
    public ResponseEntity<Iterable<Review>> searchByReviewTitle(@RequestParam(name = "title") String title) {
        try {
            return new ResponseEntity<>(reviewService.finByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/questions/user-id")
    public ResponseEntity<Iterable<Question>> findQuestionByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(questionService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/answers/user-id")
    public ResponseEntity<Iterable<Answer>> findAnswerByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(answerService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reviews/user-id")
    public ResponseEntity<Iterable<Review>> findReviewByUserId(@RequestParam(name = "user-id") Long userId) {
        try {
            return new ResponseEntity<>(reviewService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reviews-vote/game-id")
    public ResponseEntity<Iterable<Integer>> getReviewsByGameId(@RequestParam(name = "game-id") Long gameId) {
        try {
            return new ResponseEntity<>(reviewService.getReviewsVoteByGameId(gameId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/answers/question")
    public ResponseEntity<Iterable<Answer>> findAnswerByQuestionId(@RequestParam(name = "question-id") Long questionId) {
        try {
            return new ResponseEntity<>(answerService.findByQuestion(questionId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*---------------------POST---------------------*/

    @PostMapping("/new-question")
    public ResponseEntity<Question> createNewQuestion(@RequestBody Question post) {
        try {
            return new ResponseEntity<>(questionService.save(post), HttpStatus.OK);
        } catch (Exception | ByIdNotFoundException e) {
            log.error("Error saving post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/new-answer")
    public ResponseEntity<Answer> createNewAnswer(@RequestBody Answer post) {
        try {
            return new ResponseEntity<>(answerService.save(post), HttpStatus.OK);
        } catch (Exception | ByIdNotFoundException e) {
            log.error("Error saving post: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

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
