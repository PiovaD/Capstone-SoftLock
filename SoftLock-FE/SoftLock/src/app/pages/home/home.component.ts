import { Component, OnInit } from '@angular/core';
import { IPost } from 'src/app/Models/posts/ipost';
import { IReview } from 'src/app/Models/posts/ireview';
import { PostService } from 'src/app/Services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  reviews!: IReview[];

  questions!: IPost[];

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.postService.getAllPosts<IReview>("reviews")
    .subscribe((res)  => this.reviews = res)

    this.postService.getAllPosts<IPost>("questions")
    .subscribe((res)  => this.questions = res)
  }

}
