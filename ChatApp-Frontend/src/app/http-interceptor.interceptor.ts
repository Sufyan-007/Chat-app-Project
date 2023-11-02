import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class HttpCustomInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    console.log('Intercepted request');
    return next.handle(request).pipe(
      catchError((error) => {
        if (error.code == 403) {
          console.log('Error');
          alert('You have been Logged out');
          localStorage.removeItem('token');
          this.router.navigate(['']);
        }
        return throwError(() => error);
      })
    );
  }
}
