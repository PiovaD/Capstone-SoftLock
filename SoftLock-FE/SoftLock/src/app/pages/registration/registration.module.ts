import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegistrationRoutingModule } from './registration-routing.module';
import { RegistrationComponent } from './registration.component';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';
import {AvatarModule} from 'primeng/avatar';

import { PasswordFormComponent } from 'src/app//Components/password-form/password-form.component';
import { AvatarFormComponent } from 'src/app//Components/avatar-form/avatar-form.component';
import { UserFormComponent } from 'src/app/Components/user-form/user-form.component';


@NgModule({
  declarations: [
    RegistrationComponent,
    UserFormComponent,
    AvatarFormComponent,
    PasswordFormComponent
  ],
  imports: [
    CommonModule,
    RegistrationRoutingModule,
    FormSharedModule,
    AvatarModule
  ]
})
export class RegistrationModule { }
