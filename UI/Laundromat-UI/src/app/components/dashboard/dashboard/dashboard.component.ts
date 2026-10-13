import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { StockService } from 'src/app/services/stock/stock.service';
import { LoginComponent } from '../../login/login/login.component';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  selectedGraph!: String;

  constructor(private _router: Router, private stockService: StockService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.testText();
    //this.getStockInfo();
  }


getStockInfo(){

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

  public chartDatasets_line: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset' },
    { data: [100, 48, 40, 19, 86, 27, 90], label: 'My Second dataset' },
    { data: [12, 14, 18, 34, 56, 32, 2, 77], label: 'My third dataset' }
  ];

  public chartDatasets_bar: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset' }
  ];

  public chartDatasets_pie: Array<any> = [
    { data: [300, 50, 100, 40, 120], label: 'My First dataset' }
  ];

  public chartDatasets_radar: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'My First dataset' },
    { data: [28, 48, 40, 19, 86, 27, 90], label: 'My Second dataset' }
  ];

  public chartLabels_line: Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'Aug'];

  public chartLabels_bar: Array<any> = ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'];

  public chartLabels_pie: Array<any> = ['Red', 'Green', 'Yellow', 'Grey', 'Dark Grey'];

  public chartLabels_radar: Array<any> = ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'];

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

  public chartColors_bar: Array<any> = [
    {
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 2,
    }
  ];

  public chartColors_pie: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870', '#A8B3C5', '#616774'],
      borderWidth: 2,
    }
  ];

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

  public chartOptions_line: any = {
    responsive: true
  };

  public chartOptions_bar: any = {
    responsive: true
  };

  public chartOptions_pie: any = {
    responsive: true
  };

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

  bashbtnclick(rt: string) {
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
      case 'logout':
        this.authService.logout();
        this._router.navigate(['login']);
        break;

      default:
        break;
    }
  }

}


