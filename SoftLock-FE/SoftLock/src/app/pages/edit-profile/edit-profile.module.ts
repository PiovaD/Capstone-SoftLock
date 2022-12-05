import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EditProfileRoutingModule } from './edit-profile-routing.module';
import { EditProfileComponent } from './edit-profile.component';
import { RegistrationModule } from '../registration/registration.module';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';


@NgModule({
    declarations: [
        EditProfileComponent
    ],
    imports: [
        CommonModule,
        EditProfileRoutingModule,
        RegistrationModule,
        ConfirmDialogModule
    ]
})
export class EditProfileModule { }
