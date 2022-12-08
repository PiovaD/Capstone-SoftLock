import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { IGame } from 'src/app/Models/games/igame';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';
import { AuthService } from 'src/app/Services/auth.service';
import { GameService } from 'src/app/Services/game.service';
import { PostService } from 'src/app/Services/post.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss'],
  providers: [MessageService]
})
export class GameComponent implements OnInit {

  game?: IGame;
  user?: IUser;

  ratings: number[] = [];;
  rating: number = 0;

  reviews: IReview[] = [];
  questions: IPost[] = [];

  displayQuestionForm: boolean = false;
  displayReviewForm: boolean = false;

  questionForm!: FormGroup;
  reviewForm!: FormGroup;

  isUpdate: boolean = false;

  isLoading: boolean = false;

  constructor(
    private messageService: MessageService,
    private authService: AuthService,
    private userService: UserService,
    private gameService: GameService,
    private postService: PostService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.questionForm = this.formBuilder.group({
      title: [null, [Validators.required, Validators.minLength(3)]],
      text: [null, [Validators.required, Validators.minLength(10)]],
      user: null,
      game: null
    });

    this.reviewForm = this.formBuilder.group({
      id: null,
      title: [null, [Validators.required, Validators.minLength(3)]],
      text: [null, [Validators.required, Validators.minLength(10)]],
      vote: [1, [Validators.required]],
      user: null,
      game: null
    });

    this.route.params.subscribe(res => {
      this.gameService.getGameBySlug(res['slug'])
        .subscribe({
          next: (res) => {
            this.game = res
            this.game.releaseDate = new Date(this.game.releaseDate)
          },
          complete: () => {
            this.setReview()
            this.getTopReviews()
            this.getTopQuestions()
            this.getUser()
          },
          error: () => this.router.navigate(['/'])
        })
    })
  }

  setReview(): void {
    if (this.game) {
      this.postService.getReviewVotesByGameId(this.game.id)
        .subscribe({
          next: (res) => {
            this.ratings = res;
            this.rating = (res.reduce((a, b) => a + b, 0)) / res.length;
          }
        })
    }
  }

  getTopReviews(): void {
    if (this.game) {
      this.postService.getByGameId<IPost>(this.game.id, "/questions")
        .subscribe(res => {
          res.sort((a, b) => a.downVote.length - b.downVote.length)
          this.questions = res
        })
    }
  }

  getTopQuestions(): void {
    if (this.game) {
      this.postService.getByGameId<IReview>(this.game.id, "/reviews")
        .subscribe(res => {
          res.sort((a, b) => a.downVote.length - b.downVote.length)
          this.reviews = res
        })
    }
  }

  getUser(): void {
    let user: UserAuthRes | null = this.authService.getLoggedUser()
    if (user) {
      this.userService.getUserById(user.id)
        .subscribe(res => {
          this.user = res
          this.questionForm.setValue({
            title: null,
            text: null,
            user: this.user,
            game: this.game
          })

          let review: IReview | undefined = this.reviews.find(r => r.user.id == this.user?.id && r.game.id == this.game?.id)

          this.isUpdate = review ? true : false

          this.reviewForm.setValue({
            id: review?.id || null,
            title: review?.title || null,
            text: review?.text || null,
            vote: review?.vote || 1,
            user: this.user,
            game: this.game
          })
        })
    }

  }

  dateDiffInDays(): number {
    const _MS_PER_DAY = 1000 * 60 * 60 * 24;

    if (this.game) {
      const utc1: number = this.game?.releaseDate.valueOf()
      const utc2: number = Date.now()

      return Math.floor((utc2 - utc1) / _MS_PER_DAY);
    }

    return 0

  }

  canReview(): boolean {
    if (this.user) {
      return this.user.roles.find(role => role.roleType.toString() != "ROLE_USER") != undefined ? true : false
    }
    return false
  }

  submitQuestion(): void {
    this.isLoading = true;
    this.postService.createNewPost<IPost>("question", this.questionForm.value)
      .subscribe({
        next: (res) => this.questions?.unshift(res),
        error: (err) => {
          console.error('httpError', err);
          this.isLoading = false;
          err.status == 0 ?
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
            :
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Submit question failed, retry later' });

          setTimeout(
            () => {
              this.messageService.clear()
            }, 3000);
        }
      })

    this.isLoading = false;
    this.displayQuestionForm = false;
  }

  submitReview(): void {
    this.isLoading = true;

    if (!this.isUpdate) {
      this.postService.createNewPost<IReview>("review", this.reviewForm.value)
        .subscribe({
          next: (res) => this.reviews.unshift(res),
          error: (err) => {
            console.error('httpError', err);
            this.isLoading = false;
            err.status == 0 ?
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
              :
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Submit review failed, retry later' });

            setTimeout(
              () => {
                this.messageService.clear()
              }, 3000);
          }
        })

    } else {
      this.postService.updatePost<IReview>("review", this.reviewForm.value)
        .subscribe({
          next: (res) => this.reviews.splice(this.reviews.findIndex(r => r.user.id == this.user?.id && r.game.id == this.game?.id),1,res),
          error: (err) => {
            console.error('httpError', err);
            this.isLoading = false;
            err.status == 0 ?
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
              :
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Submit review failed, retry later' });

            setTimeout(
              () => {
                this.messageService.clear()
              }, 3000);
          }
        })
    }

    this.isLoading = false;
    this.displayReviewForm = false;
  }
}

