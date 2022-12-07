import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReviewsRoutingModule } from './reviews-routing.module';
import { ReviewsComponent } from './reviews.component';
import { PostCardModule } from 'src/app/Components/post-card/post-card.module';
import { DividerModule } from 'primeng/divider';


@NgModule({
  declarations: [
    ReviewsComponent
  ],
  imports: [
    CommonModule,
    ReviewsRoutingModule,
    PostCardModule,
    DividerModule
  ]
})
export class ReviewsModule { }
