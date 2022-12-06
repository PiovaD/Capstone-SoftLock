import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PasswordFormComponent } from './password-form.component';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';



@NgModule({
  declarations: [PasswordFormComponent],
  exports: [PasswordFormComponent],
  imports: [
    CommonModule,
    FormSharedModule
  ]
})
export class PasswordFormModule { }
