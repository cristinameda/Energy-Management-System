import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from '../service/jwt.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  let router = inject(Router);
  let jwtService = inject(JwtService);

  if (req.url.search('/auth') != -1) {
    return next(req);
  }

  const token = localStorage.getItem('TOKEN');
  if (token) {
    try {

      let isExp = jwtService.getClaim(token, 'exp') === null ? true : false;

      if (isExp) {
        localStorage.removeItem('token');
        router.navigateByUrl('/login');
        return next(req);
      }

    } catch (e) {
      localStorage.removeItem('token');
      router.navigateByUrl('/login');
      return next(req);
    }

    const modifiedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    });

    return next(modifiedReq);


  } else {
    router.navigateByUrl('/login');
    return next(req);
  }
}

