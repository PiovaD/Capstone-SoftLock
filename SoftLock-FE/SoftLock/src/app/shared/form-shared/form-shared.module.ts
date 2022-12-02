import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from "primeng/divider";
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {DialogModule} from 'primeng/dialog';
import {RadioButtonModule} from 'primeng/radiobutton';




@NgModule({
  declarations: [],
  imports: [
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    CheckboxModule,
    InputTextModule,
    ButtonModule,
    RippleModule,
    PasswordModule,
    DividerModule,
    MessagesModule,
    MessageModule,
    DialogModule,
    RadioButtonModule
  ]
})
export class FormSharedModule { }
