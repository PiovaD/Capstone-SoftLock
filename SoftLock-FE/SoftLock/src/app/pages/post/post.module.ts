import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostRoutingModule } from './post-routing.module';
import { PostComponent } from './post.component';
import { DividerModule } from 'primeng/divider';
import { PostCardModule } from 'src/app/Components/post-card/post-card.module';


@NgModule({
  declarations: [
    PostComponent
  ],
  imports: [
    CommonModule,
    PostRoutingModule,
    DividerModule,
    PostCardModule
  ]
})
export class PostModule { }
