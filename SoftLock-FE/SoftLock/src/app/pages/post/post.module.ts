import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostRoutingModule } from './post-routing.module';
import { PostComponent } from './post.component';
import { DividerModule } from 'primeng/divider';
import { PostCardModule } from 'src/app/Components/post-card/post-card.module';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';


@NgModule({
  declarations: [
    PostComponent
  ],
  imports: [
    CommonModule,
    PostRoutingModule,
    DividerModule,
    PostCardModule,
    FormSharedModule
  ]
})
export class PostModule { }
