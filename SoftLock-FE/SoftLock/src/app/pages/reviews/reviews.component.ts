import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IGame } from 'src/app/Models/games/igame';
import { IReview } from 'src/app/Models/posts/ireview';
import { GameService } from 'src/app/Services/game.service';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {

  reviews: IReview[] = [];
  game?: IGame;

  title: string = 'ALL REVIEWS'

  constructor(
    private gameService: GameService,
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(res => {

      if (res["gameSlug"] == undefined) {
        this.postService.getAllPosts<IReview>('reviews')
          .subscribe(res => this.reviews = res)

      } else {
        this.gameService.getGameBySlug(res['gameSlug'])
          .subscribe({
            next: res => this.game = res,
            complete: () => this.getPostByGame(),
            error: () => this.router.navigate(['/'])

          })
      }
    })
  }

  getPostByGame(): void {
    if (this.game) {
      this.postService.getByGameId<IReview>(this.game.id, '/reviews')
        .subscribe(res => {
          this.reviews = res
          this.title = `ALL REVIEWS OF: ${this.game?.name} `
        })
    }

  }
}
