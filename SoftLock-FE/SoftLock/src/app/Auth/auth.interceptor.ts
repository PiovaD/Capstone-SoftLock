import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authSvc: AuthService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let token = this.authSvc.getLoggedUser()?.accessToken

    if(token){

      let newRequest = request.clone({
        headers: request.headers.append('Authorization','Bearer '+ token)
      })

      return next.handle(newRequest);
    }

    return next.handle(request);

  }
}
