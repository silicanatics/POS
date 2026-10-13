import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class ItemPurchaseService {
  private apiURL!: string; // = environment.apiUrl;
// = environment.apiUrl;
  constructor(private http: HttpClient, private configService:ConfigService) { }
  getItemPurchase(controller: string) {
    this.apiURL = this.configService.config.apiUrl;
    const url = this.apiURL + controller;
   
    return this.http
      .get<any>(url).toPromise().catch(err => {console.log(err)});
  }
  
}
