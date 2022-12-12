import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmationService, ConfirmEventType, MessageService } from 'primeng/api';
import { IAnswer } from 'src/app/Models/posts/ianswer';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { UserAuthRes } from 'src/app/Models/users/auth-res';
import { IUser } from 'src/app/Models/users/iuser';
import { ValidatorService } from 'src/app/Models/validator.service';
import { AuthService } from 'src/app/Services/auth.service';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss'],
  providers: [ConfirmationService, MessageService]
})
export class PostCardComponent implements OnInit {

  @Input() post!: IPost | IAnswer | IReview;
  isLogged: boolean = false;
  user?: UserAuthRes | null;

  displayForm: boolean = false;
  isLoading: boolean = false;
  postForm!: FormGroup;

  vote: number = 0;

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private authService: AuthService,
    private postService: PostService,
    private formBuilder: FormBuilder,
    private router: Router,
    private validationService: ValidatorService
  ) { }

  ngOnInit(): void {

    this.postForm = this.formBuilder.group({
      id: null,
      title: [null, [Validators.required, Validators.minLength(3)], this.validationService.titleValidator],
      text: [null, [Validators.required, Validators.minLength(10)]],
      vote: 1,
      user: null,
      game: null
    });

    if (this.isReview(this.post)) this.vote = (this.post as IReview).vote
    this.user = this.authService.getLoggedUser();

    this.isLogged = this.user ? true : false;

  }

  isReview(post: IPost | IAnswer | IReview): post is IReview {
    return (<IReview>post).vote !== undefined;
  }

  isAnswer(post: IPost | IAnswer | IReview): post is IAnswer {
    return (<IAnswer>this.post).question !== undefined;
  }

  isUserPost(post: IPost | IAnswer | IReview): boolean {
    return post.user.id == this.user?.id
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

  getDate(): string {
    return new Date(this.post.date).toLocaleString()
  }

  deletePost(post: IPost | IAnswer | IReview): void {
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.postService.deletePost<IPost | IAnswer | IReview>(post)
          .subscribe(() => {
            this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Record deleted' });
            this.refresh()
          })
      },

      reject: (type: ConfirmEventType) => {
        switch (type) {
          case ConfirmEventType.REJECT:
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected' });
            break;
          case ConfirmEventType.CANCEL:
            this.messageService.add({ severity: 'warn', summary: 'Cancelled', detail: 'You have cancelled' });
            break;
        }
      }
    });
  }

  updatePost(post: IReview | IPost | IAnswer): void {
    this.displayForm = true
    this.postForm.setValue({
      id: post?.id || null,
      title: post?.title || "null",
      text: post?.text || null,
      vote: (post as IReview).vote || null,
      user: post.user,
      game: post.game

    })
  }

  submitPost(): void {

    if (this.isReview(this.post)) {
      this.update("review")

    } else if (this.isAnswer(this.post)) {
      this.update("answer")

    } else {
      this.update("question")

    }

    this.displayForm = false
  }

  update(url: string): void {
    this.postService.updatePost<IReview | IPost | IAnswer>(url, this.postForm.value)
      .subscribe({
        complete: () => this.refresh(),
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Rejected', detail: err.message });
        }
      })
  }

  refresh(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/game', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl])
    })
  }

  getRoleClass(user: IUser): string {
    return this.authService.getClassByUserRole(user)
  }

}
