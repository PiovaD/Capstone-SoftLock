import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SearchRoutingModule } from './search-routing.module';
import { SearchComponent } from './search.component';

import { PostCardModule } from 'src/app/Components/post-card/post-card.module';
import {TabViewModule} from 'primeng/tabview';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import { DividerModule } from 'primeng/divider';


@NgModule({
  declarations: [
    SearchComponent
  ],
  imports: [
    CommonModule,
    SearchRoutingModule,
    PostCardModule,
    TabViewModule,
    ProgressSpinnerModule,
    DividerModule
  ]
})
export class SearchModule { }
