import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserFormComponent } from './user-form.component';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';



@NgModule({
  declarations: [UserFormComponent],
  exports: [UserFormComponent],
  imports: [
    CommonModule,
    FormSharedModule
  ]
})
export class UserFormModule { }
