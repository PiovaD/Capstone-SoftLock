import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IGame } from 'src/app/Models/games/igame';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { GameService } from 'src/app/Services/game.service';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  questionsFind: IPost[] = []
  reviewsFind: IReview[] = []
  gamesFind: IGame[] = []

  gamesLoading: boolean = false

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
    private gameService: GameService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.gamesLoading = true
      this.getReviews(params['q'])
      this.getQuestions(params['q'])
      this.getGames(params['q'])
    })
  }

  getReviews(title: string): void {
    this.postService.findPostsByTitle<IReview>("review", title)
      .subscribe(res => this.reviewsFind = res.sort((a, b) => (a.upVote.length - a.downVote.length) > (b.upVote.length - b.downVote.length) || new Date(a.date) > new Date(b.date) ? -1 : 1))
  }

  getQuestions(title: string): void {
    this.postService.findPostsByTitle<IPost>("question", title)
      .subscribe(res => this.questionsFind = res.sort((a, b) => (a.upVote.length - a.downVote.length) > (b.upVote.length - b.downVote.length) || new Date(a.date) > new Date(b.date) ? -1 : 1))
  }

  getGames(title: string): void {
    this.gameService.getGameInIgdb(title, 10)
      .subscribe(res => {
        this.gamesFind = res
        this.gamesLoading = false
      })
  }

  openGame(game: IGame): void {
    this.gameService.getGameByIgdbId(game.igdbID)
    .subscribe({
      next: (res) => console.log(res),
      complete: () => this.router.navigate(['/game/'+ game.slug]),
      error: () => {
        this.gameService.addFormIgdbGame(game.igdbID)
        .subscribe(() => this.router.navigate(['/game/'+ game.slug]))
      }
    })

  }

}
