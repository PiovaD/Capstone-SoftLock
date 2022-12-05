import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { IGame } from '../Models/games/igame';
import { Page } from '../Models/page';

type gameParams = {
  genre?: string
  platform?: string
}

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  getAllGames(): Observable<IGame[]> {
    return this.http.get<IGame[]>(API + 'games');
  }

  getAllGamesPaginate(params: { [param: string]: string | number | boolean }): Observable<Page<IGame>> {
    return this.http.get<Page<IGame>>(API + 'games/pageable', { params });
  }

  getGameById(id: number | string): Observable<IGame> {
    return this.http.get<IGame>(API + 'games/igdb-id', { params: { "id": id } });
  }

  getGameByIgdbId(id: number | string): Observable<IGame> {
    return this.http.get<IGame>(API + 'games/id', { params: { "id": id } });
  }

  getGameByExactName(name: string): Observable<IGame> {
    return this.http.get<IGame>(API + 'games/exact-name', { params: { "name": name } });
  }

  getGameBySlug(slug: string): Observable<IGame> {
    return this.http.get<IGame>(API + 'games/game/' + slug);
  }

  getGameInIgdb(name: string, size: number): Observable<IGame[]> {
    return this.http.get<IGame[]>(API + 'games/game-igdb', { params: { "name": name, "size": size } });
  }

  findByGeneresAndPlatform(params: gameParams): Observable<IGame[]> {
    return this.http.get<IGame[]>(API + 'games/find', { params });
  }

  addFormIgdbGame(igdbId: number): Observable<IGame> {
    return this.http.post<IGame>(API + 'games/add/' + igdbId, igdbId);
  }

}
