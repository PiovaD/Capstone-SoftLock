import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';
import { PostService } from 'src/app/Services/post.service';
import { IPost } from 'src/app/Models/posts/ipost';
import { IAnswer } from 'src/app/Models/posts/ianswer';
import { IReview } from 'src/app/Models/posts/ireview';
import { SelectItem } from 'primeng/api';
import { RoleType } from 'src/app/Models/users/role-type';
import { IRole } from 'src/app/Models/users/irole';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit { //TODO fetch  post up vote e down vote

  user?: IUser;
  loggedUser?: UserAuthRes;

  posts: IPost[] | IAnswer[] | IReview[] = [];
  upVote: number = 0;
  downVote: number = 0;

  roles: IRole[] = []

  selectedRoles: IRole[] = []

  constructor(
    private authServ: AuthService,
    private userServ: UserService,
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {

    this.loggedUser = this.authServ.getLoggedUser() || undefined;

    this.route.params.subscribe(res => {
      this.userServ.getUserByUsername(res['username'])
        .subscribe({
          next: (res) => {
            this.user = res
            this.getAllRoles()
            this.getSelectedRoles()
          },
          complete: () => this.getPosts(),
          error: () => this.router.navigate(['/'])
        })
    })

  }

  isAdmin(): boolean {
    if(this.loggedUser){
      return this.loggedUser.roles.find(role=> role == "ROLE_DEV" || role == "ROLE_ADMIN")? true : false;
    }
    return false;
  }

  getPosts(): void {
    if (this.user) {
      this.postService.getByUserId<IPost | IAnswer | IReview>(this.user.id)
        .subscribe(
          res => {
            this.posts = res
            this.posts.map(res => {
              res.date = new Date(res.date)
              this.upVote += res.upVote.length
              this.downVote += res.downVote.length
            })

            this.posts.sort((a, b) => <any>b.date - <any>a.date)
          }
        )
    }
  }

  getAllRoles(): void {
    this.userServ.getAllRoles().subscribe(res => this.roles = res)
  }

  getSelectedRoles(): void {
    if (this.user) {
      this.user.roles.forEach(
        role => this.selectedRoles.push(role)
      )
    }
  }

  getRoleClass(): string {
    if (this.user)
      return this.authServ.getClassByUserRole(this.user)
    return ""
  }

  saveRoles(): void {
    if (this.user) {
      this.user.roles = this.selectedRoles

      this.userServ.setRolesToUser(this.user)
        .subscribe({
          complete: () => this.refresh(),
          error: (err) => {
            console.log(err);
            this.refresh()
          }
        })
    }
  }

  refresh(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/game', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl])
    });
  }

}
