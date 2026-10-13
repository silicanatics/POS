import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { AuthenticationRequest } from 'src/app/entities/AuthenticationRequest';
import { Usr } from 'src/app/entities/Usr';
import { environment } from 'src/environments/environment';
import { JWTTokenService } from '../localstore/JWTTokenService';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiURL!: string; // =  this.configService.config.apiUrl;//environment.apiUrl;
// =  this.configService.config.apiUrl;//environment.apiUrl;
  public user!:Usr ;
  private currentUserSubject!: BehaviorSubject<any>;
  private refreshTokenTimeout!:NodeJS.Timeout;
  
  constructor(private http: HttpClient, private configService: ConfigService,
    private jWTTokenService: JWTTokenService) { 
    this.currentUserSubject = new BehaviorSubject<any>(null);
  }

  authenticate(controller:string, authRequest:AuthenticationRequest) {
    this.apiURL =  this.configService.config.apiUrl
    console.log('config', this.configService.config);
    this.apiURL = this.configService.config.apiUrl;
    const url = this.apiURL + controller ;
    return this.http
    .post<any>(url,authRequest).toPromise()
    .then(res => {
      this.user = res; 
      localStorage.setItem('currentUser', JSON.stringify(res));
      this.currentUserSubject.next(res);
      this.jWTTokenService.setToken(res.token);
      this.startRefreshTokenTimer();
      return res.token;
    }) 
    .catch(err => {console.log(err)});
             
}

logout() {
  // remove user from local storage to log user out
  this.jWTTokenService.decodeToken();
  console.log(this.jWTTokenService.getDecodeToken());
  let tm = <number><unknown>this.jWTTokenService.getExpiryTime();
  console.log(tm);
  let dt:Date = new Date(tm * 1000);
  console.log(dt);
  this.jWTTokenService.getExpiryTime();
  localStorage.removeItem('currentUser');
  localStorage.removeItem('token');
  this.currentUserSubject.next(null);
  
}



private startRefreshTokenTimer() {
  // parse json object from base64 encoded jwt token
  console.log(this.user.token);
  const jwtToken = JSON.parse(atob(this.user.token.split('.')[1]));
  const exp = <number><unknown>this.jWTTokenService.getExpiryTime();
  // set a timeout to refresh the token a minute before it expires
  const expires = new Date(exp * 1000);
  const timeout = expires.getTime() - Date.now() - (60 * 1000);
  const expiresDT = new Date(timeout);
  // console.log("startRefreshTokenTimer");
  // console.log(jwtToken);
   console.log("expires");
   console.log(expires);
  // console.log(timeout);

  this.refreshTokenTimeout = setTimeout(() => this.refreshToken("/auth/refreshtoken").subscribe(), timeout);
}

refreshToken(controller: string) {
  const httpOptions = {
    headers: new HttpHeaders({
       'isRefreshToken': 'true',
       'Authorization': this.user.token
    })
  };
  const params = new HttpParams()
  .set('isRefreshToken', 'true')
  .set('Authorization', this.user.token);
  
  const url = this.apiURL + controller ;
  return this.http.get<any>(url, {headers: this.createTokenHeader()})
      .pipe(map((tkn) => {
        this.user.token = tkn.token;
          this.currentUserSubject.next(this.user);
          localStorage.removeItem('currentUser');
          localStorage.removeItem('token');
          localStorage.setItem('currentUser', JSON.stringify(this.user));
          localStorage.setItem('token',tkn.token);
          console.log(this.user);
          this.jWTTokenService.setToken(tkn.token);
          this.startRefreshTokenTimer();
          return this.user;
      }));
}

public createTokenHeader(): HttpHeaders {
  let reqOptions = new HttpHeaders().set('Content-Type','application/json')
  if(this.user.token) {
     reqOptions = new HttpHeaders().set( 'Content-Type', 'application/json').set('Authorization', `Bearer ${this.user.token}`);
  }
  return reqOptions;
}


}
