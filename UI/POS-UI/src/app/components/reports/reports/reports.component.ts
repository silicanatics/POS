import { Component, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { Router } from '@angular/router';
import { DailyPurchasesReport } from 'src/app/entities/DailyPurchasesReport';
import { Report } from 'src/app/entities/Report';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  loading:boolean=false;
  invoiceImage: any;
  invoiceImage12:any;
  date!: Date;
  startDate!:Date;
  endDate!:Date;
  reports: Report[] = [];
  dailyPurchasesReport!: DailyPurchasesReport;
  reportId!:number;

  constructor(private _router: Router, private reportService: ReportService) { }

  dashboard() {
    this._router.navigate(['dashboard'])
  }

  ngOnInit(): void {
    this.reports = [{ id: 1, description: 'Items Sold Report' },
    { id: 2, description: 'Month Days Report' },
    { id: 3, description: 'Accountant Report' },]
  
  }
  
  OnDateChange(date:Date){
    this.date = new Date(date);
    console.log(this.date);   
  }

  OnStartDateChange(date:Date){
    this.startDate = new Date(date);
    console.log(this.startDate);
    this.date = new Date(date);
  }

  OnEndDateChange(date:Date){
    this.endDate = new Date(date);
    console.log(this.endDate);
  }

  changeClient(value: MatSelectChange) {
    console.log(value.value);
    
    // if (value.value == 1){
      this.reportId = value.value;
     // this.generateItemsSoldReport();
    // }
  }

  
  gotodashboard() {
   // this.focused = false;
    this._router.navigate(['dashboard'])
  }
   

}


  // test(){
  //   //let dd:Date = this.date
  //   this.getInvoice();
  // }

  // async getInvoice() {
  //   this.loading = true;
  //   let res: any = await this.reportService.getItemsSoldImage("/report/getItemsSoldReportImage?PurchaseDate=" + this.date);
  //   var reader = new FileReader();
  //   reader.onloadend = () => {
  //     this.invoiceImage = reader.result;
  //   }
  //   reader.readAsDataURL(res);
  //   this.loading = false;
  // }