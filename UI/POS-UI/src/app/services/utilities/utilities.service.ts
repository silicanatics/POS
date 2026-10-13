import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class UtilitiesService {

  private apiURL: string = environment.apiUrl;
  constructor(private http: HttpClient, private configService:ConfigService) { 
    this.apiURL = this.configService.config.apiUrl;
  }

  getPrinterInfo(controller: string) {
    this.apiURL = this.configService.config.apiUrl;
    const url = this.apiURL + controller;
    console.log(url);
    return this.http
      .get<any>(url).toPromise().catch(err => { console.log(err) });
  }
}
