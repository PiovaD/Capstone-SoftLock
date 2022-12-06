import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostCardComponent } from './post-card.component';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import {ScrollPanelModule} from 'primeng/scrollpanel';

import {AvatarModule} from 'primeng/avatar';
import { CardModule } from 'primeng/card';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    PostCardComponent
  ],
  exports: [PostCardComponent],
  imports: [
    CommonModule,
    RouterModule,
    RatingModule,
    FormsModule,
    ScrollPanelModule,
    AvatarModule,
    CardModule
  ]
})
export class PostCardModule { }
