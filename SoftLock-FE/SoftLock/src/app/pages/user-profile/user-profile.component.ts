import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/Auth/user.service';
import { IUser } from 'src/app/Models/iuser';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit { //TODO fetch  post up vote e down vote

  user?: IUser;

  posts?: any[];

  upVote?: any[];

  downVote?: any[];

  constructor(private userServ: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {

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
