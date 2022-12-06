import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IAnswer } from 'src/app/Models/posts/ianswer';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  post?: IPost | IReview;

  review!: IReview;

  answers?: IAnswer[];

  constructor(private postService: PostService, private route: ActivatedRoute, private router: Router) { }


  ngOnInit(): void {

    this.route.params.subscribe(res => {
      this.postService.getPostBySlug<IPost | IReview>(res['slug'])
        .subscribe({
          next: (res) => {
            this.post = res
          },
          complete: () => this.setAnswers(),
          error: () => this.router.navigate(['/'])
        })
    })



  }

  setAnswers(){
      if(!this.isReview() && this.post){

      this.postService.getAnswersByQuestionId(this.post.id)
      .subscribe({
        next: (res) => this.answers = res,
        error: () => this.router.navigate(['/'])
      })
    }
  }


  isReview(): boolean {
    return (<IReview>this.post).vote !== undefined;
  }


}
