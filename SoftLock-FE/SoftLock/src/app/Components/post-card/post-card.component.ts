import { Component, Input, OnInit } from '@angular/core';
import { IAnswer } from 'src/app/Models/posts/ianswer';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';
import { AuthService } from 'src/app/Services/auth.service';
import { PostService } from 'src/app/Services/post.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss']
})
export class PostCardComponent implements OnInit {

  @Input() post!: IPost | IAnswer | IReview;
  isLogged: boolean = false;
  user?: UserAuthRes | null;

  vote: number = 0;

  constructor(private authService: AuthService, private userService: UserService, private postService: PostService) { } //todo like e non

  ngOnInit(): void {
    if (this.isReview(this.post)) this.vote = (this.post as IReview).vote
    this.user = this.authService.getLoggedUser();

    this.isLogged = this.user ? true : false;

  }

  isReview(post: IPost | IAnswer | IReview): post is IReview {
    return (<IReview>post).vote !== undefined;
  }

  isLoggedAndUpVoted(): boolean {
    if (this.user) {
      return (this.post.upVote.find(el => el.id == this.user?.id)) != undefined ? true : false
    }
    return false;
  }

  isLoggedAndDownVoted(): boolean {
    if (this.user) {
      return (this.post.downVote.find(el => el.id == this.user?.id)) != undefined ? true : false
    }
    return false;
  }

  toggleUpVote(): void {
    if (this.user) {
      if (this.isLoggedAndUpVoted()) {
        this.postService.removeVote<IPost | IAnswer | IReview>(this.post.id, this.user?.id).subscribe(
          res => this.post = res
        )
      } else {
        this.postService.addUpVote<IPost | IAnswer | IReview>(this.post.id, this.user?.id).subscribe(
          res => this.post = res
        )
      }
    }
  }

  toggleDownVote(): void {
    if (this.user) {
      if (this.isLoggedAndDownVoted() && this.user) {
        this.postService.removeVote<IPost | IAnswer | IReview>(this.post.id, this.user?.id).subscribe(
          res => this.post = res
        )
      } else {
        this.postService.addDownVote<IPost | IAnswer | IReview>(this.post.id, this.user?.id).subscribe(
          res => this.post = res
        )
      }
    }
  }

}
