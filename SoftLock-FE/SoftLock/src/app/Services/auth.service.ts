import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { UserAuthRes } from '../Models/users/auth-res';
import { IUser } from '../Models/users/iuser';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) { }

  register(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(API + 'users/new', user);
  }

  login(user: IUser): Observable<UserAuthRes> {
    return this.http.post<UserAuthRes>(API + 'login', user);
  }

  saveAccess(data: UserAuthRes, remember: boolean): void {
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

  getLoggedUser(): UserAuthRes | null {

    return JSON.parse(
      String(localStorage.getItem('access') || sessionStorage.getItem('access'))
    );
  }

}
