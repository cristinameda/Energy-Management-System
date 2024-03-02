import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  saveUser(user: User): Observable<any> {
    return this.http.post<User>(this.baseURL + '/users', user);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseURL + '/users');
  }

  deleteUserById(id: string): Observable<any> {
    return this.http.delete<any>(this.baseURL + '/users/' + id);
  }

  updateUser(user: User): Observable<any>{
    return this.http.put<User>(this.baseURL + '/users', user);
  }

  getUsersByRole(role: string): Observable<User[]> {
    return this.http.get<User[]>(this.baseURL + '/users/role/' + role);
  }

  getUserById(id: BigInt): Observable<User> {
    return this.http.get<User>(this.baseURL + '/users/' + id);
  }
}
