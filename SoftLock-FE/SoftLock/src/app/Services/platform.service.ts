import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { IPlatform } from '../Models/games/iplatform';
import { Page } from '../Models/page';

type platformParams = {
  name?: string,
  slug?: string,
  abbreviation?: string,
  igdbID?: number
}

@Injectable({
  providedIn: 'root'
})
export class PlatformService {

  constructor(private http: HttpClient) { }

  getAllPlatforms(): Observable<IPlatform[]> {
    return this.http.get<IPlatform[]>(API + 'platforms');
  }

  getAllPlatformsPaginate(page: number, size: number): Observable<Page<IPlatform>> {
    return this.http.get<Page<IPlatform>>(API + 'platforms/pageable', { params: { "page": page, "size": size } });
  }

  getPlatformById(id: number | string): Observable<IPlatform> {
    return this.http.get<IPlatform>(API + 'platforms/igdb-id', { params: { "id": id } });
  }

  getPlatformByIgdbId(id: number | string): Observable<IPlatform> {
    return this.http.get<IPlatform>(API + 'platforms/id', { params: { "id": id } });
  }

  getPlatformByExactName(name: string): Observable<IPlatform> {
    return this.http.get<IPlatform>(API + 'platforms/exact-name', { params: { "name": name } });
  }

  getPlatformByAbbreviation(name: string): Observable<IPlatform> {
    return this.http.get<IPlatform>(API + 'platforms/abbreviation', { params: { "name": name } });
  }

  getPlatformBySlug(name: string): Observable<IPlatform> {
    return this.http.get<IPlatform>(API + 'platforms/platform/' + name);
  }

  findByGeneresAndPlatform(params: platformParams): Observable<IPlatform[]> {

    return this.http.get<IPlatform[]>(API + 'platforms/find', { params });

  }
}
