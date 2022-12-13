import { Component, OnInit } from '@angular/core';
import { IGame } from 'src/app/Models/games/igame';
import { GameService } from 'src/app/Services/game.service';

@Component({
  selector: 'app-game-carousel',
  templateUrl: './game-carousel.component.html',
  styleUrls: ['./game-carousel.component.scss']
})
export class GameCarouselComponent implements OnInit {

  games: IGame[] = []
  responsiveOptions: { breakpoint: string; numVisible: number; numScroll: number; }[];

  constructor(private gameService: GameService) {
    this.responsiveOptions = [

      {
        breakpoint: '1500px',
        numVisible: 3,
        numScroll: 1
      },
      {
        breakpoint: '868px',
        numVisible: 2,
        numScroll: 1
      },
      {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1
      }
    ];
  }

  ngOnInit(): void {

    this.gameService.getAllGamesPaginate({ page: 0, size: 10, sort: "releaseDate,desc", })
      .subscribe(res => {
        this.games = res.content;
      })


  }

}
