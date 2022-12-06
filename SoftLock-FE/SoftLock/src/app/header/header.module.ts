import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header.component';

import {InputTextModule} from 'primeng/inputtext';
import {AvatarModule} from 'primeng/avatar';
import { RouterModule } from '@angular/router';
import { StyleClassModule } from 'primeng/styleclass';
import {RippleModule} from 'primeng/ripple';
import { ButtonModule } from 'primeng/button';
import {MenubarModule} from 'primeng/menubar';




@NgModule({
  declarations: [HeaderComponent],
  exports: [HeaderComponent],
  imports: [
    CommonModule,
    RouterModule,
    InputTextModule,
    AvatarModule,
    StyleClassModule,
    RippleModule,
    ButtonModule,
    MenubarModule
  ]
})
export class HeaderModule { }
