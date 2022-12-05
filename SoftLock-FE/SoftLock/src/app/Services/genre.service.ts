import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { IGenre } from '../Models/games/igenre';
import { Page } from '../Models/page';

type genreParams = {
  name?: string,
  slug?: string,
  igdbID?: number
}

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  constructor(private http: HttpClient) { }

  getAllGenres(): Observable<IGenre[]> {
    return this.http.get<IGenre[]>(API + 'genres');
  }

  getAllGenresPaginate(page: number, size: number): Observable<Page<IGenre>> {
    return this.http.get<Page<IGenre>>(API + 'genres/pageable', { params: { "page": page, "size": size } });
  }

  getGenreById(id: number | string): Observable<IGenre> {
    return this.http.get<IGenre>(API + 'genres/igdb-id', { params: { "id": id } });
  }

  getGenreByIgdbId(id: number | string): Observable<IGenre> {
    return this.http.get<IGenre>(API + 'genres/id', { params: { "id": id } });
  }

  getGenreByExactName(name: string): Observable<IGenre> {
    return this.http.get<IGenre>(API + 'genres/exact-name', { params: { "name": name } });
  }

  getGenreBySlug(slug: string): Observable<IGenre> {
    return this.http.get<IGenre>(API + 'genres/genre/' + slug);
  }

  findByGeneresAndPlatform(params: genreParams): Observable<IGenre[]> {

    return this.http.get<IGenre[]>(API + 'genres/find', { params });

  }

}
