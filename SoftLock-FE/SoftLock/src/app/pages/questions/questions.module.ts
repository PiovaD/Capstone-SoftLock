import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionsRoutingModule } from './questions-routing.module';
import { QuestionsComponent } from './questions.component';
import { PostCardModule } from 'src/app/Components/post-card/post-card.module';
import { DividerModule } from 'primeng/divider';


@NgModule({
  declarations: [
    QuestionsComponent
  ],
  imports: [
    CommonModule,
    QuestionsRoutingModule,
    PostCardModule,
    DividerModule
  ]
})
export class QuestionsModule { }
