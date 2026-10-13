import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  private apiURL: string = environment.apiUrl;
  constructor(private http: HttpClient, private configService:ConfigService) { 
    this.apiURL = this.configService.config.apiUrl;
  }

  getStockInfo(controller: string) : Observable<any> {
    const url = this.apiURL + controller;
    return this.http
      .get<any>(url);
  }
}
