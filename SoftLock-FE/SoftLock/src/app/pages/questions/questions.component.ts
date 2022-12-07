import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IGame } from 'src/app/Models/games/igame';
import { IPost } from 'src/app/Models/posts/ipost';
import { GameService } from 'src/app/Services/game.service';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.scss']
})
export class QuestionsComponent implements OnInit {

  questions: IPost[] = [];
  game?: IGame;

  title: string = 'ALL QUESTIONS'

  constructor(
    private gameService: GameService,
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(res => {

      if (res["gameSlug"] == undefined) {
        this.postService.getAllPosts<IPost>('questions')
          .subscribe(res => this.questions = res)

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

  getPostByGame(): void{
    if (this.game){
      this.postService.getByGameId<IPost>(this.game.id,'/questions')
      .subscribe(res =>
        {
        this.questions = res
        this.title = `ALL QUESTION OF: ${this.game?.name} `
      })
    }
  }
}
