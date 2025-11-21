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
  const token = authService.getToken();

  /* Add token to the request headers if present */
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  /* Handle authentication errors */
  return next(req).pipe(
    catchError((error) => {
      if (error.status === 401) {
        /* Invalid or expired token */
        authService.logout();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};
