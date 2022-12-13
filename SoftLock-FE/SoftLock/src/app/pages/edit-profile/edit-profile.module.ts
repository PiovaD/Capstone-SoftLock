import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EditProfileRoutingModule } from './edit-profile-routing.module';
import { EditProfileComponent } from './edit-profile.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { UserFormModule } from 'src/app/Components/user-form/user-form.module';
import { PasswordFormModule } from 'src/app/Components/password-form/password-form.module';
import { AvatarFormModule } from 'src/app/Components/avatar-form/avatar-form.module';
import { FormSharedModule } from 'src/app/shared/form-shared/form-shared.module';


@NgModule({
    declarations: [
        EditProfileComponent
    ],
    imports: [
        CommonModule,
        EditProfileRoutingModule,
        UserFormModule,
        PasswordFormModule,
        AvatarFormModule,
        FormSharedModule,
        ConfirmDialogModule
    ]
})
export class EditProfileModule { }
