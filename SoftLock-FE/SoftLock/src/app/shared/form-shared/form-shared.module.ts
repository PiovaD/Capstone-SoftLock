import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from "primeng/divider";
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { DialogModule } from 'primeng/dialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TooltipModule } from 'primeng/tooltip'
import { InputTextareaModule } from 'primeng/inputtextarea';
import {AutoFocusModule} from 'primeng/autofocus';




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
    RadioButtonModule,
    TooltipModule,
    InputTextareaModule,
    AutoFocusModule
  ]
})
export class FormSharedModule { }
