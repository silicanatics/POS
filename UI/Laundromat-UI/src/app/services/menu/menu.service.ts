import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ItemOption } from 'src/app/entities/ItemOption';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuItemOption } from 'src/app/entities/MenuItemOption';
import { MenuItemPayload } from 'src/app/entities/MenuItemPayload';
import { MenuNode } from 'src/app/entities/MenuNode';
import { PurchasePayload } from 'src/app/entities/PurchasePayload';
import { PurchaseType } from 'src/app/entities/PurchaseType';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../system_config/config.service';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private apiURL: string = environment.apiUrl;
  constructor(private http: HttpClient, private configService:ConfigService) { 
    this.apiURL = this.configService.config.apiUrl;
  }

  getMenuItems(controller: string, mode: number) {
    this.apiURL = this.configService.config.apiUrl;
      const url = this.apiURL + controller;
      return this.http
        .get<any>(url).toPromise().catch(err => {console.log(err)});
  }

  
  getMenuItemTypes(controller: string, mode: number) {
    const url = this.apiURL + controller;
    return this.http
      .get<any>(url).toPromise().catch(err => {console.log(err)});
}

getMenuItemOptions(controller: string, mode: number) {
  const url = this.apiURL + controller;
  return this.http
    .get<any>(url).toPromise().catch(err => {console.log(err)});
}



getItemOptions(controller: string, mode: number) {
  const url = this.apiURL + controller;
  return this.http
    .get<any>(url).toPromise().catch(err => {console.log(err)});
}

saveMenuItem(controller: string, menuItem: MenuItem) {
  const url = this.apiURL + controller;
  return this.http
    .post<any>(url,menuItem).toPromise().catch(err => {console.log(err)});
}

getPurchaseInvoiveNumber(controller: string){
  const url = this.apiURL + controller;
  return this.http
    .get<any>(url).toPromise().catch(err => {console.log(err)});
}


testPrintInvoice(controller: string, purchasePayload: PurchasePayload) : Observable<any> {
  let test:PurchaseType = new PurchaseType();
  test.description = "sadasd";
  test.id=12;
  var config = {
    headers : {
        'Content-Type': 'application/json'
    }
};

 // const url = "http://localhost/thermalPrint/api/thermalPrint/printInvoice";
  const url = "http://localhost:64109/api/thermalPrint/printInvoice";
  return this.http
    .post<PurchaseType>(url,purchasePayload,config);
}



savePurchaseItems(controller: string, purchasePayload: PurchasePayload) : Observable<any> {
  const url = this.apiURL + controller;
  return this.http
    .post<any>(url,purchasePayload);
}

async saveMenuItemOptions(controller: string, menuItemPayload: MenuItemPayload) {
  const url = this.apiURL + controller;
 
  return this.http
    .post<any>(url,menuItemPayload).toPromise().catch(err => {console.log(err)});
}

async saveMenuNode(controller: string, menuItem: MenuItem) {
  const url = this.apiURL + controller;
 
  return this.http
    .post<any>(url,menuItem).toPromise().catch(err => {console.log(err)});
}

async saveItemOption(controller: string, itemOption: ItemOption) {
  const url = this.apiURL + controller;
  console.log(itemOption);
  return this.http
    .post<any>(url,itemOption).toPromise().catch(err => {console.log(err)});
}



savePurchase(){
  const url = "";
}

}
