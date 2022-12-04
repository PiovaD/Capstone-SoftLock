import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EditProfileRoutingModule } from './edit-profile-routing.module';
import { EditProfileComponent } from './edit-profile.component';
import { RegistrationModule } from '../registration/registration.module';


@NgModule({
    declarations: [
        EditProfileComponent
    ],
    imports: [
        CommonModule,
        EditProfileRoutingModule,
        RegistrationModule
    ]
})
export class EditProfileModule { }
