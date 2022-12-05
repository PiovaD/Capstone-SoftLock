import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmationService, ConfirmEventType, MessageService } from 'primeng/api';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';
import { ValidatorService } from 'src/app/Models/validator.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss'],
  providers: [ConfirmationService, MessageService]
})
export class EditProfileComponent implements OnInit {

  loggedUser!: UserAuthRes | null;
  user!: IUser;

  updateForm!: FormGroup;
  passwordForm!: FormGroup;

  isLoading: boolean = false;
  display: boolean = false;


  constructor(
    private confirmationService: ConfirmationService,
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

    this.createPasswordForm()

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

            this.passwordForm.setValue({
              id: this.user.id,
              password: null,
              confirm: null
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

  createPasswordForm(): void {
    this.passwordForm = this.formBuilder.group({
      id: null,
      password: [null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{5,}$')]],
      confirm: [null, [this.confirmValidator]]
    });
  }

  submitForm(): void {

    this.userService.updateUser(this.updateForm.value)
      .subscribe({
        next: (res) => {
          let currStore = JSON.parse(String(localStorage.getItem('access') || String(sessionStorage.getItem('access'))))

          currStore.email = res.email;
          currStore.username = res.username;
          currStore.profilePicUrl = res.profilePicUrl;

          localStorage.setItem('access', JSON.stringify(currStore))

          this.user = res;

          this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'User updated' })

        },
        complete: () => {
          setTimeout(
            () => {
              this.location.back();
            }, 1000);

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

  confirmValidator = (control: FormControl) : { [s: string]: boolean } | null => {

    if (control.value && control.value != this.passwordForm.controls['password'].value) {

          console.info(control.value + " " + this.passwordForm.controls['password'].value)
      return { error: true };
    }
    return null;
  };

  deleteUser(): void {

    this.confirmationService.confirm({
      message: 'Do you want to delete the account?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.userService.deleteUser(this.user)
          .subscribe({
            next: () => {
              this.authService.removeAccess()
              this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'User deleted' });
            },

            complete: () => {
              setTimeout(
                () => {
                  this.router.navigate(['/'])
                }, 1000);
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
      },
      reject: (type: ConfirmEventType) => {
        switch (type) {
          case ConfirmEventType.REJECT:
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected' });
            break;
        }

        setTimeout(
          () => {
            this.messageService.clear()
          }, 3000);
      }
    });




  }

  isDev(): boolean {
    return !(this.loggedUser?.roles.includes("ROLE_DEV"));
  }

  submitPassword():void{
  this.userService.updateUserPassword(this.passwordForm.value)
    .subscribe({

      next: () => this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Password updated' }),

      complete: () => {
        setTimeout(
          () => {
        this.location.back();
          }, 1000);
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

}
