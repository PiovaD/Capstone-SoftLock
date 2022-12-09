import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';
import { AuthService } from '../Services/auth.service';
import { PostService } from '../Services/post.service';
import { UserService } from '../Services/user.service';
import { IPost } from './posts/ipost';
import { IUser } from './users/iuser';

@Injectable({
  providedIn: 'root'
})
export class ValidatorService {

  constructor(private authService: AuthService, private userService: UserService, private postService: PostService) { }

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

  usernameValidator = (control: AbstractControl): Promise<ValidationErrors | null> => {
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

  titleValidator = (control: AbstractControl): Promise<ValidationErrors | null> => {
    return new Promise<ValidationErrors | null>((resolve) => {
      this.postService.getAllPosts<IPost>()
        .subscribe((res) => {

          if (res.find((post: IPost) =>
            post.title != null ?
              (post.title.toUpperCase() == control.value.toUpperCase()) && (control.parent?.value['id'] != post.id)
              : false
          )) {

            resolve({ prohibitedData: true, warning: true })

          } else {

            resolve(null)

          }
        });
    });
  }
}
