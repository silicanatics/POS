import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PurchasePayload } from 'src/app/entities/PurchasePayload';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiURL: string = environment.apiUrl;
  
  constructor(private http: HttpClient, private configService:ConfigService) { 
    this.apiURL = this.configService.config.apiUrl;
  }

  async getImage(urlTMP: string,purchasePayload:PurchasePayload) {
    const url = this.apiURL + urlTMP;
    return this.http.post(url,purchasePayload, { responseType: 'blob' }).toPromise().catch(err => {console.log(err)});
 
  }

  async getItemsSoldImage(urlTMP: string) {
    const url = this.apiURL + urlTMP;
    return this.http.get(url, { responseType: 'blob' }).toPromise().catch(err => {console.log(err)});
 
  }

  async getDailyPurchasesReport(urlTMP: string) {
    const url = this.apiURL + urlTMP;
    return this.http.get<any>(url).toPromise().catch(err => {console.log(err)});
 
  }


}
