import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { Page } from '../Models/page';
import { IPost } from '../Models/posts/ipost';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }


  /*---------------------GET---------------------*/

  getAllPosts<T>(url?: string): Observable<T[]> {
    return this.http.get<T[]>(API + 'posts/' + url);
  }

  getAllPostsPaginate<T>(params: { [param: string]: string | number | boolean }, url?: string): Observable<Page<T>> {
    return this.http.get<Page<T>>(API + 'posts/' + url + 'pageable', { params });
  }

  getPostById<T>(id: number | string): Observable<T> {
    return this.http.get<T>(API + 'posts/id', { params: { "id": id } });
  }

  getGameBySlug<T>(slug: string): Observable<T> {
    return this.http.get<T>(API + 'posts/title/' + slug);
  }

  getByPostId<T>(gameId: string, url?: string): Observable<T> {
    return this.http.get<T>(API + 'posts' + url + '/game-id', { params: { "game-id": gameId } });
  }

  getByUserId<T>(userId: string, url?: string): Observable<T[]> {
    return this.http.get<T[]>(API + 'posts' + url + '/user-id', { params: { "user-id": userId } });
  }

  getGameByTitle<T>(title: string): Observable<T[]> {
    return this.http.get<T[]>(API + 'posts/title', { params: { "title": title } });
  }

  findPostsByNomeAndTitle<T>(name: string, title: string): Observable<T[]> {
    return this.http.get<T[]>(API + 'posts/search', { params: { "post-name": name, "title": title } });
  }

  getAnswerByQuestionId<T>(questionID: number): Observable<T[]> {
    return this.http.get<T[]>(API + 'posts/answers/question', { params: { "question-id": questionID } });
  }

  getReviewVotesByGameId(id: number | string): Observable<number[]> {
    return this.http.get<number[]>(API + 'posts/reviews/game-id', { params: { "game-id": id } });
  }

  /*---------------------POST---------------------*/

  createNewPost<T>(url: string, post: T): Observable<T> {
    return this.http.post<T>(API + `posts/new-${url}`, post);
  }

  /*---------------------PUT---------------------*/

  updatePost<T>(url: string, post: T): Observable<T> {
    return this.http.post<T>(API + `posts/update-${url}`, post);
  }

  addUpVote<T>(postId: number, userId: number): Observable<T> {
    return this.http.post<T>(`${API}posts/up-vote/add/${postId}/${userId}`, null);
  }

  addDownVote<T>(postId: number, userId: number): Observable<T> {
    return this.http.post<T>(`${API}posts/down-vote/add/${postId}/${userId}`, null);
  }

  /*---------------------DELETE---------------------*/

  removeDownVote<T>(postId: number, userId: number): Observable<T> {
    return this.http.delete<T>(`${API}posts/vote/remove/${postId}/${userId}`);
  }

  deletePost<T>(post: T): Observable<T>{
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }), body: post
    };
    return this.http.delete<T>(`${API}posts/delete`, httpOptions);
  }

}
