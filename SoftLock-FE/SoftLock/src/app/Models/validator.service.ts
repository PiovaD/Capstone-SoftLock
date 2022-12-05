import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';
import { AuthService } from '../Auth/auth.service';
import { UserService } from '../Auth/user.service';
import { IUser } from './users/iuser';

@Injectable({
  providedIn: 'root'
})
export class ValidatorService {

  constructor(private authService: AuthService, private userService: UserService) { }

  emailValidator = (control: AbstractControl): Promise<ValidationErrors | null> => {
    let currentUser = this.authService.getLoggedUser()
    return new Promise<ValidationErrors | null>((resolve) => {
      this.userService.getAllUsers()
        .subscribe((res) => {
          if (res.find((user: IUser) =>
            (user.email == control.value) &&
            (user.email != currentUser?.email))) {
            resolve({ prohibitedData: true, warning: true })
          } else {
            resolve(null)
          }
        });
    });
  };

  usernameValidator = (control: AbstractControl) => {
    let currentUser = this.authService.getLoggedUser()
    return new Promise<ValidationErrors | null>((resolve) => {
      this.userService.getAllUsers().subscribe((res) => {
        if (res.find((user: IUser) =>

          (user.username.toUpperCase() == control.value.toUpperCase()) &&
          (user.username.toUpperCase() != currentUser?.username.toUpperCase())
        )) {
          resolve({ prohibitedData: true, warning: true })
        } else {
          resolve(null)
        }
      });
    });
  };
}
