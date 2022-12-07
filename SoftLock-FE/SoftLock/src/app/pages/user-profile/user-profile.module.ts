import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserProfileRoutingModule } from './user-profile-routing.module';
import { UserProfileComponent } from './user-profile.component';
import {DividerModule} from 'primeng/divider';
import {ButtonModule} from 'primeng/button';
import { PostCardModule } from 'src/app/Components/post-card/post-card.module';


@NgModule({
  declarations: [
    UserProfileComponent
  ],
  imports: [
    CommonModule,
    DividerModule,
    UserProfileRoutingModule,
    ButtonModule,
    PostCardModule
  ]
})
export class UserProfileModule { }
