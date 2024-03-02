import { Component } from '@angular/core';
import { NavComponent } from '../nav/nav.component';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavComponent, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private authService: AuthService,
    private router: Router) {
    if(!this.authService.isUserAuthenticated()) {
      this.router.navigateByUrl('/login');
    }
  }

}
