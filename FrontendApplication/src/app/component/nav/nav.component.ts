import { Component, OnInit } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { CommonModule } from '@angular/common';
import { UserComponent } from '../user/user.component';
import { ChatComponent } from '../chat/chat.component';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {

  isAuthenticated?: boolean;
  userRoles: string | null = '';

  constructor(
    private authService: AuthService
  ) {
    this.isAuthenticated = this.authService.isUserAuthenticated();
  }

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isUserAuthenticated();
    this.userRoles = this.authService.getLoggedUserRoles();
  }

  logout() {
    this.authService.logout();
  }

}
