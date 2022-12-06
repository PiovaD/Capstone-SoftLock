import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AvatarFormComponent } from './avatar-form.component';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';

import {AvatarModule} from 'primeng/avatar';



@NgModule({
  declarations: [AvatarFormComponent],
  exports: [AvatarFormComponent],
  imports: [
    CommonModule,
    FormSharedModule,
    AvatarModule
  ]
})
export class AvatarFormModule { }
