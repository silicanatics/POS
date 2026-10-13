import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  apiURL!: string;
  constructor(private authService:AuthService, private configService:ConfigService) {
    this.apiURL = this.configService.config.apiUrl;
  }

  // private apiURL: string = environment.apiUrl;
  

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.apiURL = this.configService.config.apiUrl;
    const currentUser = this.authService.user;
    const isLoggedIn = currentUser && currentUser.token;
    const isApiUrl = request.url.startsWith(this.apiURL);
    if (isLoggedIn && isApiUrl) {
        request = request.clone({
            setHeaders: {
                Authorization: `Bearer ${currentUser.token}`
            }
        });
    }
    return next.handle(request);
  }
}
