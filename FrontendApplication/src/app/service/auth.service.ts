import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseURL = 'http://localhost:8080';
  
  constructor(private http: HttpClient, private jwtService: JwtService) { }

  login(user: {
    username: string,
    password: string
  }): Observable<any> {
    return this.http.post(this.baseURL + '/auth/login', user).pipe(
      tap((response: any) => this.doLoginUser(user.username, response.token)));
  }

  register(user: {
    name: string,
    username: string,
    password: string,
    address: string,
    birthday: string
  }): Observable<any> {
    return this.http.post(this.baseURL + '/auth/register', user).pipe(
      tap((response: any) => this.doLoginUser(user.username, response.token)));
  }

  private doLoginUser(username: string, token: any) {
    this.storeJwtToken(token);
    this.storeUsername(username);
    let userId = this.jwtService.getClaim(token, 'id');
    this.storeId(userId);
    let roles = this.jwtService.getClaim(token, 'roles');
    this.storeRole(roles);
  }

  private storeJwtToken(token: any) {
    localStorage.setItem('TOKEN', token);
  }

  private storeUsername(username: string) {
    localStorage.setItem('USER', username);
  }

  private storeRole(role: any) {
    localStorage.setItem('ROLE', role);
  }

  private storeId(id: any) {
    localStorage.setItem('ID', id);
  }

  isUserAuthenticated(): boolean {
    return localStorage.getItem('USER') !== undefined;
  }

  getLoggedUser(): string | null {
    return localStorage.getItem('USER');
  }

  getLoggedUserId(): string | null {
    return localStorage.getItem('ID');
  }

  getLoggedUserRoles(): string | null {
    return localStorage.getItem('ROLE');
  }

  logout() {
    localStorage.removeItem('TOKEN');
    localStorage.removeItem('USER');
    localStorage.removeItem('ID');
    localStorage.removeItem('ROLE');
  }
}
