import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './service/auth.service';
import { UserService } from './service/user.service';
import { HomeComponent } from './component/home/home.component';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { NavComponent } from './component/nav/nav.component';
import { UserComponent } from './component/user/user.component';
import { ChatComponent } from './component/chat/chat.component';
import { DeviceComponent } from './component/device/device.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, HomeComponent, LoginComponent, RegisterComponent, NavComponent, UserComponent, ChatComponent, DeviceComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'FrontendApplication';

  authService = inject(AuthService);
  userService = inject(UserService);

  constructor() {}
    //   this.authService.login(
  //     {
  //       username: 'cristinameda',
  //       password: 'Password123!'
  //     }
  //   ).subscribe((response) => {
  //     console.log(response);
  //     this.userService.getUsers().subscribe((users) => {
  //       users.forEach((user) => console.log(user));
  //     })
  //   })
}
