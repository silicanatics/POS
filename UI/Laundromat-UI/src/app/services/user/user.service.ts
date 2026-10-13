import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usr } from 'src/app/entities/Usr';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiURL: string = environment.apiUrl;
  constructor(private http: HttpClient, private configService:ConfigService) {
    this.apiURL = this.configService.config.apiUrl;
   }

  async saveUser(controller:string, user:Usr){
    this.apiURL = this.configService.config.apiUrl;
    const url = this.apiURL + controller ;
    return this.http
    .post<any>(url,user).toPromise()
    .then(res => {
 
      return res;
    }) 
    .catch(err => {console.log(err)});
  }
}
