import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { JwtService } from '../service/jwt.service';

export const authGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  let routeURL = state.url;
  let token = localStorage.getItem("TOKEN");
  let jwtService = inject(JwtService);

  return new Promise((resolve) => {

    if (token == null && routeURL !== '/login') {
      routeURL = 'login';
      router.navigate(['/login'], {
        queryParams: {
          return: 'login'
        }
      });
      return resolve(false);
    } else if (token != null) {
      const role = jwtService.getClaim(token, "roles");

      if (role == 'CLIENT' && (routeURL === "/users")) {
        routeURL = '';
        router.navigate(['/'], {
          queryParams: {
            return: ''
          }
        });
        return resolve(false);
      } else if (role == 'ADMIN') {
        routeURL = router.url;
        return resolve(true);
      } else if (role == 'CLIENT') {
        routeURL = router.url;
        return resolve(true);
      }
    } else {
      routeURL = router.url;
      return resolve(true);
    }
  })
};
