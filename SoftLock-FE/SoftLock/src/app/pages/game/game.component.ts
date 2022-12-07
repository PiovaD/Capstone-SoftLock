import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IGame } from 'src/app/Models/games/igame';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { GameService } from 'src/app/Services/game.service';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  game?: IGame;

  ratings: number[] = [];;
  rating: number = 0;

  reviews: IReview[] = [];

  questions: IPost[] = [];

  constructor(
    private gameService: GameService,
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {

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
          },
          error: () => this.router.navigate(['/'])
        })
    })
  }

  setReview() {
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
      this.postService.getByGameId<IReview>(this.game.id,"/reviews")
        .subscribe(res => {
          res.sort((a, b) => a.downVote.length - b.downVote.length)
          this.reviews = res
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

}

