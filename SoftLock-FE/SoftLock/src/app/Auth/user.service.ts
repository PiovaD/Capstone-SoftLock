import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API } from 'src/environments/environment';
import { IUser } from '../Models/iuser';
import { RoleType } from '../Models/role-type';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<IUser[]> {
    return this.http.get<IUser[]>(API + 'users');
  }

  getAllUsersPaginate(page: number, size: number): Observable<IUser> {
    return this.http.get<IUser>(API + 'users/pageable', { params: { "page": page, "size": size } });
  }

  getUserById(id: number | string): Observable<IUser> {
    return this.http.get<IUser>(API + 'users/id', { params: { "id": id } });
  }

  getUserByUsername(username: string): Observable<IUser> {
    return this.http.get<IUser>(API + 'users/username', { params: { "username": username } });
  }

  updateUser(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(API + 'users/update', user);
  }

  updateProfilePic(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(API + 'users/update-profile-pic', user);
  }

  updateUserPassword(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(API + 'users/update-password', user);
  }

  addRoleUser(user: IUser, role: RoleType): Observable<IUser> {
    return this.http.put<IUser>(API + 'users/add-role/' + role, user);
  }

  removeRole(user: IUser, role: RoleType): Observable<IUser> {
    return this.http.delete<IUser>(API + 'users/remove-role/'+ role, { body: { user } }); //TODO: Verifica se funziona altrimetni fallo con l'id
  }

  deleteUser(user: IUser): Observable<IUser> {
    return this.http.delete<IUser>(API + 'users/delete', { body: { user } }); //TODO: Verifica se funziona altrimetni fallo con l'id
  }

}
