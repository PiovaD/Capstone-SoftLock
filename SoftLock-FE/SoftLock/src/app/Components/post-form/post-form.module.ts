import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostFormComponent } from './post-form.component';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';
import { InputTextareaModule } from 'primeng/inputtextarea';



@NgModule({
  declarations: [
    PostFormComponent
  ],
  exports: [
    PostFormComponent
  ],
  imports: [
    CommonModule,
    FormSharedModule,
    InputTextareaModule
  ]
})
export class PostFormModule { }
