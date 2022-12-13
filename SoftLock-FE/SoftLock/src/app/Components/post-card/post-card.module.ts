import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostCardComponent } from './post-card.component';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { ScrollPanelModule } from 'primeng/scrollpanel';

import { AvatarModule } from 'primeng/avatar';
import { CardModule } from 'primeng/card';
import { RouterModule } from '@angular/router';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { PostFormModule } from '../post-form/post-form.module';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';


@NgModule({
  declarations: [
    PostCardComponent
  ],
  exports: [
    PostCardComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    RatingModule,
    FormsModule,
    ScrollPanelModule,
    AvatarModule,
    CardModule,
    ConfirmDialogModule,
    ToastModule,
    DialogModule,
    PostFormModule,
    FormSharedModule

  ]
})
export class PostCardModule { }
