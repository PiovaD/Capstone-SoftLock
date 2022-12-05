import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/Auth/auth.service';
import { UserService } from 'src/app/Auth/user.service';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit { //TODO fetch  post up vote e down vote

  user?: IUser;
  loggedUser?: UserAuthRes;

  posts?: any[];

  upVote?: any[];

  downVote?: any[];

  constructor(private authServ: AuthService , private userServ: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {

    this.loggedUser = this.authServ.getLoggedUser() || undefined;

    this.route.params.subscribe(res => {
      this.userServ.getUserByUsername(res['username'])
        .subscribe({
          next: (res) => {
            this.user = res
          },
          error: () => this.router.navigate(['/'])
        })
    })

  }

}
