import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GameRoutingModule } from './game-routing.module';
import { GameComponent } from './game.component';
import {DividerModule} from 'primeng/divider';
import { ChipModule } from 'primeng/chip';
import {RatingModule} from 'primeng/rating';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    GameComponent
  ],
  imports: [
    CommonModule,
    GameRoutingModule,
    DividerModule,
    ChipModule,
    RatingModule,
    FormsModule
  ]
})
export class GameModule { }