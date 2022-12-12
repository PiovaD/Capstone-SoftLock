import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserProfileRoutingModule } from './user-profile-routing.module';
import { UserProfileComponent } from './user-profile.component';
import { DividerModule } from 'primeng/divider';
import { ButtonModule } from 'primeng/button';
import { PostCardModule } from 'src/app/Components/post-card/post-card.module';
import { MultiSelectModule } from 'primeng/multiselect';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    UserProfileComponent
  ],
  imports: [
    CommonModule,
    DividerModule,
    UserProfileRoutingModule,
    ButtonModule,
    PostCardModule,
    MultiSelectModule,
    FormsModule,

  ]
})
export class UserProfileModule { }
