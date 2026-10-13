import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private apiURL: string = environment.apiUrl;
  constructor(private http: HttpClient, private configService: ConfigService ) {
    this.apiURL =  this.configService.config.apiUrl;
   }

  getCustomers(controller: string) {
    this.apiURL = this.configService.config.apiUrl;
    const url = this.apiURL + controller;
    return this.http
      .get<any>(url).toPromise().catch(err => { console.log(err) });
  }
}
