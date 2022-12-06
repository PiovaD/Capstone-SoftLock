import { Component, Input, OnInit } from '@angular/core';
import { IAnswer } from 'src/app/Models/posts/ianswer';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss']
})
export class PostCardComponent implements OnInit {

  @Input() post!: IPost | IAnswer | IReview;
  isLogged: boolean = false;
  user?: UserAuthRes | null;

  vote:number = 0;

  constructor(private authService: AuthService) { } //todo like e non

  ngOnInit(): void {
   if(this.isReview(this.post)) this.vote = (this.post as IReview).vote
   this.user = this.authService.getLoggedUser();

   this.isLogged = this.user? true: false;

  }

  isReview(post: IPost | IAnswer | IReview): post is IReview {
    return (<IReview>post).vote !== undefined;
 }

}
