import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { IUser } from '../Models/iuser';

type AuthRes = {
  accessToken: string;
  id: number;
  username: string;
  profilePicUrl: string | null;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) { }

  register(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(API + 'users/new', user);
  }

  login(user: IUser): Observable<AuthRes> {
    return this.http.post<AuthRes>(API + 'login', user);
  }

  saveAccess(data: AuthRes, remember: boolean): void {
    this.removeAccess();

    if (remember) {
      localStorage.setItem('access', JSON.stringify(data));
    } else {
      sessionStorage.setItem('access', JSON.stringify(data));
    }
  }

  removeAccess(): void {
    localStorage.removeItem('access');
    sessionStorage.removeItem('access');
  }

  getLoggedUser(): AuthRes | null {

    return JSON.parse(
      String(localStorage.getItem('access') || sessionStorage.getItem('access'))
    );
  }

}
