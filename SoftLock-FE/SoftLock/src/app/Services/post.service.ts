import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getPostByGameId(id: number | string): Observable<number[]> {
    return this.http.get<number[]>(API + 'posts/reviews/game-id', { params: { "game-id": id } });
  }
}
