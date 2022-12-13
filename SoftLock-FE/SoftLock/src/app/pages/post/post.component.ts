import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { IAnswer } from 'src/app/Models/posts/ianswer';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';
import { AuthService } from 'src/app/Services/auth.service';
import { PostService } from 'src/app/Services/post.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
  providers: [MessageService]
})
export class PostComponent implements OnInit {

  post?: IPost | IReview;
  user?: UserAuthRes | IUser | null;

  review!: IReview;
  answers?: IAnswer[];

  display: boolean = false;
  isLoading: boolean = false;

  replyForm!: FormGroup;

  constructor(
    private messageService: MessageService,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }


  ngOnInit(): void {

    this.user = this.authService.getLoggedUser()

    this.route.params.subscribe(res => {
      this.postService.getPostBySlug<IPost | IReview>(res['slug'])
        .subscribe({
          next: (res) => {
            this.post = res
            this.getUser()
          },
          complete: () => this.setAnswers(),
          error: () => this.router.navigate(['/'])
        })
    })

    this.replyForm = this.formBuilder.group({
      text: [null, [Validators.required, Validators.minLength(10)]],
      user: null,
      question: null,
      game: null
    });

  }

  getUser(): void {
    if (this.user) {
      this.userService.getUserById(this.user?.id)
        .subscribe(res => {
          this.user = res
          this.replyForm.setValue({
            text: null,
            user: this.user,
            question: this.post,
            game: this.post?.game
          })
        })
    }
  }

  setAnswers() {
    if (!this.isReview() && this.post) {

      this.postService.getAnswersByQuestionId(this.post.id)
        .subscribe({
          next: (res) => this.answers = res.sort((a, b) => (a.upVote.length - a.downVote.length) > (b.upVote.length - b.downVote.length) || new Date(a.date) > new Date(b.date) ? -1 : 1),
          error: () => this.router.navigate(['/'])
        })
    }
  }

  isReview(): boolean {
    return (<IReview>this.post).vote !== undefined;
  }

  submitForm(): void {

    console.log(this.replyForm.value)

    this.postService.createNewPost<IAnswer>("answer", this.replyForm.value)
      .subscribe({
        next: (res) => this.answers?.unshift(res),
        error: (err) => {
          console.error('httpError', err);
          this.isLoading = false;
          err.status == 0 ?
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Server error' })
            :
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Reply failed, retry later' });

          setTimeout(
            () => {
              this.messageService.clear()
            }, 3000);
        }
      })

    this.display = false;
  }


}
