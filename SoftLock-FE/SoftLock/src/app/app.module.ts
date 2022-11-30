import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import {RippleModule} from 'primeng/ripple';
import {ButtonModule} from 'primeng/button';
import {MenubarModule} from 'primeng/menubar';
import {StyleClassModule} from 'primeng/styleclass';
import {AvatarModule} from 'primeng/avatar';
import {InputTextModule} from 'primeng/inputtext';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RippleModule,
    ButtonModule,
    MenubarModule,
    StyleClassModule,
    AvatarModule,
    InputTextModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
