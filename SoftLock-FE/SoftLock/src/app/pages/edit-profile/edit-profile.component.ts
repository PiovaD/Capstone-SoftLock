import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/Auth/auth.service';
import { UserService } from 'src/app/Auth/user.service';
import { AuthRes } from 'src/app/Models/auth-res';
import { IUser } from 'src/app/Models/iuser';
import { RoleType } from 'src/app/Models/role-type';
import { ValidatorService } from 'src/app/Models/validator.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss'],
  providers: [MessageService]
})
export class EditProfileComponent implements OnInit {

  loggedUser!: AuthRes | null;
  user!: IUser;

  updateForm!: FormGroup;
  isLoading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private validationService: ValidatorService,
    private location: Location,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {

    this.loggedUser = this.authService.getLoggedUser();

    this.createForm()

    if (this.loggedUser != null) {

      this.userService.getUserByUsername(this.loggedUser.username)
        .subscribe({
          next: (res) => this.user = res,
          complete: () => {

            this.updateForm.setValue({
              id: this.user.id,
              name: this.user.name,
              lastName: this.user.lastName,
              username: this.user.username,
              email: this.user.email,
              profilePicUrl: this.user.profilePicUrl
            })

          },
          error: () => this.router.navigate(['/'])
        })
    } else {
      this.router.navigate(['/'])
    }
  }

  createForm(): void {
    this.updateForm = this.formBuilder.group({
      id: null,
      name: [null, [Validators.required, Validators.minLength(3)]],
      lastName: [null, [Validators.required, Validators.minLength(3)]],
      username: [
        null,
        [Validators.required, Validators.pattern('[\\S]{3,}$')],
        this.validationService.usernameValidator,
      ],
      email: [
        null,
        [Validators.email, Validators.required],
        this.validationService.emailValidator,
      ],
      profilePicUrl: [null]
    });
  }

  submitForm(): void {

    console.log("USER " + this.user.profilePicUrl)
    console.log("FORM " + this.updateForm.value.profilePicUrl)

    this.userService.updateUser(this.updateForm.value)
      .subscribe({
        next: (res) => {
          let currStore = JSON.parse(String(localStorage.getItem('access') || String(sessionStorage.getItem('access'))))

          console.log(currStore)

          currStore.email = res.email;
          currStore.username = res.username;
          currStore.profilePicUrl = res.profilePicUrl;

          localStorage.setItem('access', JSON.stringify(currStore))

          this.user = res;

          console.log(this.user)

        },
        complete: () => {
          this.location.back();
        },

        error: (err) => {
          console.error('httpError', err);
          this.isLoading = false;
          err.status == 0 ?
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
            :
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Update failed check the data' });

          setTimeout(
            () => {
              this.messageService.clear()
            }, 3000);
        }
      })

  }

  resetForm(): void {

    this.updateForm.reset({
      name: this.user.name,
      lastName: this.user.lastName,
      username: this.user.username,
      email: this.user.email,
      profilePicUrl: this.user.profilePicUrl
    });
  }

  confirmValidator = (
    control: FormControl
  ): { [s: string]: boolean } | null => {
    if (control.value && control.value != this.updateForm.value.password) {
      return { error: true };
    }
    return null;
  };

  deleteUser(): void {

    this.userService.deleteUser(this.user)
      .subscribe({
        complete: () => {
          this.authService.removeAccess()
          this.router.navigate(['/'])
        },

        error: (err) => {
          console.error('httpError', err);
          this.isLoading = false;
          err.status == 0 ?
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
            :
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Delete failed' });

          setTimeout(
            () => {
              this.messageService.clear()
            }, 3000);
        }
      })

  }

  isDev(): boolean {
    return !(this.loggedUser?.roles.includes("ROLE_DEV"));
  }

  updatePassword(): void {
    //todo: update
  }

}
