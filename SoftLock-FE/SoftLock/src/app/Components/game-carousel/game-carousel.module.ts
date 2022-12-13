import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameCarouselComponent } from './game-carousel.component';

import { CarouselModule } from 'primeng/carousel';
import { RouterModule } from '@angular/router';
import { SkeletonModule } from 'primeng/skeleton';



@NgModule({
  declarations: [
    GameCarouselComponent
  ],
  exports: [
    GameCarouselComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    CarouselModule,
    SkeletonModule
  ]
})
export class GameCarouselModule { }
