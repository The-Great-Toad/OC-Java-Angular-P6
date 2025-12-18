import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * JWT Interceptor to add the token to requests and handle auth errors
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  /* Add token to the request headers if present */
  if (authService.isAuthenticated()) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authService.getToken()}`,
      },
    });
  }

  /* Handle authentication errors */
  return next(req).pipe(
    catchError((error) => {
      if (error.status === 401 && router.url !== '/login') {
        /* Invalid or expired token */
        authService.logout();
        router.navigate(['']);
      }
      return throwError(() => error);
    })
  );
};
