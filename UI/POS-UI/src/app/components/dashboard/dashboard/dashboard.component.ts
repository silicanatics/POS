import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Application } from 'src/app/entities/Application';
import { ChartReport } from 'src/app/entities/ChartReport';
import { MenuItem } from 'src/app/entities/MenuItem';
import { AuthService } from 'src/app/services/auth/auth.service';
import { LocalStorageService } from 'src/app/services/localstore/LocalStorageService';
import { MenuService } from 'src/app/services/menu/menu.service';
import { ReportService } from 'src/app/services/report/report.service';
import { StockService } from 'src/app/services/stock/stock.service';
import { UtilitiesService } from 'src/app/services/utilities/utilities.service';
import { LoginComponent } from '../../login/login/login.component';


export class ChartDataset {
  data!: number[];
  label!: string;
}

export class ChartLabel {
  label!: string[];
}

export class ChartColor {
  backgroundColor!: string[];
  borderColor!: string[];
  borderWidth!: number;

}

export class ChartData {
  dataset!: ChartDataset;
  label!: ChartLabel;
  color!: ChartColor;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {

  chartsData: ChartData[] = [];
  selectedGraph!: String;
  date!: Date;
  reportForm!: UntypedFormGroup;
  menuItems: MenuItem[] = [];
  chartData: ChartReport[] = [];
  chartData_butcher: ChartReport[] = [];
  chartData_cakes: ChartReport[] = [];
  applications!:Application[];
  application!:Application;
  applicationKnown:boolean=false;


  public chartDatasets_bar: Array<any> = [{ data: [], label: '' }];
  public chartLabels_bar: Array<any> = [];
  public chartColors_bar: Array<any> = [{ backgroundColor: [], borderColor: [], borderWidth: 2, }];
  menuListForBarChart: MenuItem[] = [];


  chartDatasets_bar_cakes: Array<any> = [{ data: [], label: 'Cakes' }];
  public chartLabels_bar_cakes: Array<any> = [];
  public chartColors_bar_cakes: Array<any> = [{ backgroundColor: [], borderColor: [], borderWidth: 2, }];

  chartDatasets_bar_butcher: Array<any> = [{ data: [], label: 'Cakes' }];
  public chartLabels_bar_butcher: Array<any> = [];
  public chartColors_bar_butcher: Array<any> = [{ backgroundColor: [], borderColor: [], borderWidth: 2, }];

  chartDatasets_bar_montego: Array<any> = [{ data: [], label: 'Cakes' }];
  public chartLabels_bar_montego: Array<any> = [];
  public chartColors_bar_montego: Array<any> = [{ backgroundColor: [], borderColor: [], borderWidth: 2, }];

  chartDatasets_bar_groceries: Array<any> = [{ data: [], label: 'Cakes' }];
  public chartLabels_bar_groceries: Array<any> = [];
  public chartColors_bar_groceries: Array<any> = [{ backgroundColor: [], borderColor: [], borderWidth: 2, }];

  constructor(private _router: Router, private stockService: StockService, private localStorage: LocalStorageService,
             private reportService: ReportService,  private utilitiesService: UtilitiesService,
    private authService: AuthService, private formBuilder: UntypedFormBuilder, private menuService: MenuService) { }

  ngOnInit(): void {
    this.testText();
    //this.getStockInfo();
   // this.getMenus();
    //this.buildForm();
    this.getInformation();
  }

  async getInformation(){

    let data: MenuItem[] = await this.menuService.getMenuItems("/menu/getMenus?mode=1", 1);
    this.menuItems = data;

    let applicationsData = await this.utilitiesService.getPrinterInfo("/utils/getApplicationInfo");
    this.applications = applicationsData;

    if(this.applications.length == 1){
      this.application = this.applications[0];
      this.application.daysToExpirery
      this.applicationKnown = true;
      this.localStorage.set("application", JSON.stringify(this.application));
    } else 
    this.applicationKnown = false;


    this.buildForm();

  }



  buildForm() {
    this.reportForm = this.formBuilder.group({
      date: [new Date(), Validators.required]
    });
  }

  OnDateChange(date: Date) {
    this.date = new Date(date);
    console.log(this.date);
    // this.getCharts_butcher();  
    // this.getCharts_cakes();
    this.getAllCharts();
  }

  async getMenus() {

 
  }

  bashbtnclick(rt: string) {
    if(!this.applicationKnown){
      window.alert("Application not known!");
      return;
    }
    switch (rt) {
      case 'purch':
        this._router.navigate(['purchases']);
        break;
      case 'menu':
        this._router.navigate(['menumanager']);
        break;
      case 'users':
        this._router.navigate(['users']);
        break;
      case 'reports':
        this._router.navigate(['reports']);
        break;
      case 'stockcapture':
        this._router.navigate(['stockcapture']);
        break;
      case 'logout':
        this.authService.logout();
        this._router.navigate(['login']);
        break;




      default:
        break;
    }
  }

  getAllCharts() {

    const datepipe: DatePipe = new DatePipe('en-ZA');
    let strDate = datepipe.transform(this.date, 'YYYY-MM-dd');

    this.reportService.getChartReport("/report/getChartsData?menuId=0&date=" + strDate).subscribe(res => {
      console.log(res);
      this.chartData = res;
      this.buildBarChartData();
    });
  }

  async buildBarChartData() {
    let menuIds: number[] = [150, 1374, 5516, 6379, 6721];

    for (var menuId of menuIds) {

      await this.buildIds(menuId);

      let barChartData: ChartData = new ChartData();
      barChartData.dataset = new ChartDataset();
      barChartData.dataset.data = [];
      barChartData.color = new ChartColor();
      barChartData.color.backgroundColor = [];
      barChartData.color.borderColor = [];
      barChartData.label = new ChartLabel();
      barChartData.label.label = [];
      let a: number = 255,
        b: number = 0,
        c: number = 0,
        d: number = 0.6,
        col: string;

      barChartData.dataset.label = menuId.toString();
      console.log(this.menuListForBarChart);
      for (var cd of this.chartData) {
        for (var mi of this.menuListForBarChart) {
          if (cd.mi.id == mi.id) {
            a = -10; b += 10; c += 10;
            col = 'rgba(' + a + ',' + b + ',' + c + ',' + d + ')';
            barChartData.dataset.data.push(cd.value);
            barChartData.label.label.push(cd.description);
            barChartData.color.backgroundColor.push(col);
            col = 'rgba(' + a + ',' + b + ',' + c + ',1)';
            barChartData.color.borderColor.push(col);
          }
        }
      }
      if (menuId == 150) {
        this.chartDatasets_bar_cakes = [{ data: barChartData.dataset.data, label: "Cakes" }];
        this.chartLabels_bar_cakes = barChartData.label.label;
        this.chartColors_bar_cakes = [{ backgroundColor: barChartData.color.backgroundColor, borderColor: barChartData.color.borderColor }];
        this.chartsData.push(barChartData);
      } else if (menuId == 6721) {
        this.chartDatasets_bar_butcher = [{ data: barChartData.dataset.data, label: "Butcher" }];
        this.chartLabels_bar_butcher = barChartData.label.label;
        this.chartColors_bar_butcher = [{ backgroundColor: barChartData.color.backgroundColor, borderColor: barChartData.color.borderColor }];
        this.chartsData.push(barChartData);
      }
      else if (menuId == 1374) {
        this.chartDatasets_bar_montego = [{ data: barChartData.dataset.data, label: "Montego" }];
        this.chartLabels_bar_montego = barChartData.label.label;
        this.chartColors_bar_montego = [{ backgroundColor: barChartData.color.backgroundColor, borderColor: barChartData.color.borderColor }];
        this.chartsData.push(barChartData);
      } 
      else if (menuId == 5516) {
        this.chartDatasets_bar_groceries = [{ data: barChartData.dataset.data, label: "Groceries" }];
        this.chartLabels_bar_groceries = barChartData.label.label;
        this.chartColors_bar_groceries = [{ backgroundColor: barChartData.color.backgroundColor, borderColor: barChartData.color.borderColor }];
        this.chartsData.push(barChartData);
      }
    }
  }

  async buildIds(menuId: number) {

    this.menuListForBarChart = [];
    for (var mi of this.menuItems) {
      // if (mi.linkedId == 0) {
      if (mi.id == menuId) {
        console.log(mi);
        this.getAllIds(mi);
      }
    }
  }

  getAllIds(menuItem: MenuItem) {
    for (var mi of this.menuItems) {
      if (mi.linkedId == menuItem.id) {
        this.menuListForBarChart.push(mi);
        this.getAllIds(mi);
      }
    }
  }





  getCharts_butcher() {

    const datepipe: DatePipe = new DatePipe('en-ZA');
    let strDate = datepipe.transform(this.date, 'YYYY-MM-dd');

    this.reportService.getChartReport("/report/getChartsData?menuId=6722&date=" + strDate).subscribe(res => {
      console.log(res);
      this.chartData_butcher = res;
      this.buildBarChartData_butcher();
    });
  }

  getCharts_cakes() {

    const datepipe: DatePipe = new DatePipe('en-ZA');
    let strDate = datepipe.transform(this.date, 'YYYY-MM-dd');

    this.reportService.getChartReport_cakes("/report/getChartsData?menuId=150&date=" + strDate).subscribe(res => {
      console.log(res);
      this.chartData_cakes = res;
      this.buildBarChartData_cakes();
    });
  }

  buildBarChartData_butcher() {
    let data: number[] = [];
    let vals: string[] = [];
    let backgroundColor: string[] = [];
    let borderColor: string[] = [];

    let a: number = 255,
      b: number = 0,
      c: number = 0,
      d: number = 0.6,
      col: string;
    this.chartData_butcher.forEach(
      r => {
        data.push(r.value);
        vals.push(r.description);
        a = -10; b += 10; c += 10;
        col = 'rgba(' + a + ',' + b + ',' + c + ',' + d + ')';
        backgroundColor.push(col);
        col = 'rgba(' + a + ',' + b + ',' + c + ',1)';
        borderColor.push(col);
      }
    );
    this.chartDatasets_bar_butcher = [{ data: data, label: "butcher" }];
    this.chartLabels_bar_butcher = vals;
    this.chartColors_bar_butcher = [{ backgroundColor: backgroundColor, borderColor: borderColor }];

  }

  buildBarChartData_cakes() {
    let data: number[] = [];
    let vals: string[] = [];
    let backgroundColor: string[] = [];
    let borderColor: string[] = [];

    let a: number = 255,
      b: number = 0,
      c: number = 0,
      d: number = 0.6,
      col: string;
    this.chartData_cakes.forEach(
      r => {
        data.push(r.value);
        vals.push(r.description);
        a = -10; b += 10; c += 10;
        col = 'rgba(' + a + ',' + b + ',' + c + ',' + d + ')';
        backgroundColor.push(col);
        col = 'rgba(' + a + ',' + b + ',' + c + ',1)';
        borderColor.push(col);
      }
    );

    this.chartDatasets_bar_cakes = [{ data: data, label: "cakes" }];
    this.chartLabels_bar_cakes = vals;
    this.chartColors_bar_cakes = [{ backgroundColor: backgroundColor, borderColor: borderColor }];
  }

  getStockInfo() {

    let date: Date = new Date();
    const datepipe: DatePipe = new DatePipe('en-ZA');
    let stockDate = datepipe.transform(date, 'YYYY-MM-dd');

    this.stockService.getStockInfo("/stock/getStockInfo?date=" + stockDate).subscribe(
      si => {

      }
    );
  }

  testText() {

    const canvas = <HTMLCanvasElement>document.getElementById('test');
    if (canvas) {
      canvas.width = canvas.height = 100;
      let ctx = canvas.getContext('2d');
      ctx = canvas.getContext('2d');
      if (ctx) {
        ctx.font = 'italic 20px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = 'red';
        ctx.fillText('Hello!', 70, 70);
      }
    }

  }

  public chartType_line: string = 'line';
  public chartType_bar: string = 'bar';
  public chartType_pie: string = 'pie';
  public chartType_radar: string = 'radar';

  //Line***********************

  public chartDatasets_line: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset' },
    { data: [100, 48, 40, 19, 86, 27, 90], label: 'My Second dataset' },
    { data: [12, 14, 18, 34, 56, 32, 2, 77], label: 'My third dataset' }
  ];

  public chartLabels_line: Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'Aug'];

  public chartColors_line: Array<any> = [
    {
      backgroundColor: 'rgba(105, 0, 132, .2)',
      borderColor: 'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    },
    {
      backgroundColor: 'rgba(0, 137, 132, .2)',
      borderColor: 'rgba(0, 10, 130, .7)',
      borderWidth: 2,
    },
    {
      backgroundColor: 'rgba(0, 345, 132, .2)',
      borderColor: 'rgba(0, 10, 66, .7)',
      borderWidth: 2,
    }
  ];
  public chartOptions_line: any = {
    responsive: true
  };

  //Bar***********************
  // public chartDatasets_bar: Array<any> = [
  //   { data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset' }
  // ];

  // public chartLabels_bar: Array<any> = ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'];
  // public chartColors_bar: Array<any> = [
  //   {
  //     backgroundColor: [
  //       'rgba(255, 99, 132, 0.2)',
  //       'rgba(54, 162, 235, 0.2)',
  //       'rgba(255, 206, 86, 0.2)',
  //       'rgba(75, 192, 192, 0.2)',
  //       'rgba(153, 102, 255, 0.2)',
  //       'rgba(255, 159, 64, 0.2)'
  //     ],
  //     borderColor: [
  //       'rgba(255,99,132,1)',
  //       'rgba(54, 162, 235, 1)',
  //       'rgba(255, 206, 86, 1)',
  //       'rgba(75, 192, 192, 1)',
  //       'rgba(153, 102, 255, 1)',
  //       'rgba(255, 159, 64, 1)'
  //     ],
  //     borderWidth: 2,
  //   }
  // ];

  public chartOptions_bar: any = {
    responsive: true
  };
  //Pie***********************
  public chartDatasets_pie: Array<any> = [
    { data: [300, 50, 100, 40, 120], label: 'My First dataset' }
  ];
  public chartLabels_pie: Array<any> = ['Red', 'Green', 'Yellow', 'Grey', 'Dark Grey'];
  public chartColors_pie: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870', '#A8B3C5', '#616774'],
      borderWidth: 2,
    }
  ];
  public chartOptions_pie: any = {
    responsive: true
  };
  //Radar***********************
  public chartDatasets_radar: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset' },
    { data: [28, 48, 40, 19, 86, 27, 90], label: 'My Second dataset' }
  ];

  public chartLabels_radar: Array<any> = ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'];


  public chartColors_radar: Array<any> = [
    {
      backgroundColor: 'rgba(105, 0, 132, .2)',
      borderColor: 'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    },
    {
      backgroundColor: 'rgba(0, 250, 220, .2)',
      borderColor: 'rgba(0, 213, 132, .7)',
      borderWidth: 2,
    }
  ];


  public chartOptions_radar: any = {
    responsive: true
  };

  public chartClicked(e: string): void {

    switch (e) {
      case 'line':
        this.selectedGraph = 'Year To Date Sales';
        break;
      case 'bar':
        this.selectedGraph = 'Selected Daily Sales';
        break;
      case 'pie':
        this.selectedGraph = 'Year To Date Sales';
        break;
      case 'radar':
        this.selectedGraph = 'Selected Daily Sales';
        break;
      default:
        break;
    }
  }
  public chartHovered(e: any): void { }

  logout() {
    this.authService.logout();
    this._router.navigate(['login']);
  }



}


