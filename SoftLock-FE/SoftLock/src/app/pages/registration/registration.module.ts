import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegistrationRoutingModule } from './registration-routing.module';
import { RegistrationComponent } from './registration.component';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';

import { UserFormModule } from 'src/app/Components/user-form/user-form.module';
import { AvatarFormModule } from 'src/app/Components/avatar-form/avatar-form.module';
import { PasswordFormModule } from 'src/app/Components/password-form/password-form.module';


@NgModule({
  declarations: [
    RegistrationComponent,
  ],
  imports: [
    CommonModule,
    RegistrationRoutingModule,
    FormSharedModule,
    UserFormModule,
    AvatarFormModule,
    PasswordFormModule
  ]
})
export class RegistrationModule { }
