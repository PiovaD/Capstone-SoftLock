import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GameRoutingModule } from './game-routing.module';
import { GameComponent } from './game.component';
import {DividerModule} from 'primeng/divider';
import { ChipModule } from 'primeng/chip';
import {RatingModule} from 'primeng/rating';

import { PostCardModule } from 'src/app/Components/post-card/post-card.module';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';
import { PostFormModule } from 'src/app/Components/post-form/post-form.module';
import {DialogModule} from 'primeng/dialog';

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
    FormSharedModule,
    PostCardModule,
    PostFormModule,
    DialogModule
  ]
})
export class GameModule { }
