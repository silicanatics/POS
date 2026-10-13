import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { AccountantReport } from 'src/app/entities/AccountantReport';
import { BarChartColors } from 'src/app/entities/BarChartColors';
import { BarChartData } from 'src/app/entities/BarChartData';
import { DailyPurchasesReport } from 'src/app/entities/DailyPurchasesReport';
import { ItemsSoldReport } from 'src/app/entities/ItemsSoldReport';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuNode } from 'src/app/entities/MenuNode';
import { Report } from 'src/app/entities/Report';
import { MenuService } from 'src/app/services/menu/menu.service';
import { ReportService } from 'src/app/services/report/report.service';
import * as FileSaver from 'file-saver';


@Component({
  selector: 'app-accountant-report',
  templateUrl: './accountant-report.component.html',
  styleUrls: ['./accountant-report.component.css']
})
export class AccountantReportComponent implements OnInit {

  
  reports: Report[] = [];
  menuItems: MenuItem[] = [];
  selectedValue!: Report;
  dailyPurchasesReport!: DailyPurchasesReport;
  displayedColumns: string[] = ['ParentCaption', 'ParentCaptionDay', 'Caption', 'Sales', 'TotalPrice'];
  dataSource!: ItemsSoldReport[];
  //date!: Date;
  itemsSoldReportList: ItemsSoldReport[] = [];
  finalItemsSoldReportList: ItemsSoldReport[] = [];
  itemsSoldReport: ItemsSoldReport[] = [];
  itemsSoldReportTMP: ItemsSoldReport[] = [];

  chartDatasets!: Array<any>;
  barChartData!: BarChartData;
  chartLabels!: Array<any>;
  chartColors!: BarChartColors;
  barChartColors!: Array<any>;

  totalSales: number = 0;
  cashTotalSales: number = 0;
  cardTotalSales: number = 0;
  totalPrice: number = 0;
  subTotal: number = 0;
  subTotalPrice: number = 0;
  cashTotalPrice: number = 0;
  cardTotalPrice: number = 0;
  busy:boolean=true;
  reportDate!:string;
  reportDateDay!:string;

  @Input()
  startDate!: Date;
  @Input()
  endDate!: Date;

  ngOnChanges(changes: SimpleChanges) {
    // console.log(changes.startDate.currentValue);
    // console.log(changes.endDate.currentValue);
    if (changes.startDate.currentValue != undefined && changes.endDate.currentValue != undefined) {
    //  this.getMenus();
      

    }
  }


  constructor(private reportService: ReportService, private menuService: MenuService,) { }

  ngOnInit(): void {
    this.reports = [{ id: 1, description: 'Items Sold Report' },
    { id: 2, description: 'Month Days Report' },
    { id: 3, description: 'Accountant Report' },]
    this.getData();
  }


  async getMenus() {
    // this.menuLoading = true;
    let data: MenuItem[] = await this.menuService.getMenuItems("/menu/getMenus?mode=1", 1);
    console.log(data);
    this.menuItems = data.sort((a, b) => {
      if (a.caption > b.caption) {
        return 1;
      }
      if (a.caption < b.caption) {
        return -1;
      }
      return 0;
    });
    // this.menuLoading = false;

  }


  async getData() {

    this.busy = true;
    const datepipe: DatePipe = new DatePipe('en-ZA');
    let reportStartDate = datepipe.transform(this.startDate, 'YYYY-MM-dd');
    let reportEndDate = datepipe.transform(this.endDate, 'YYYY-MM-dd');
    this.chartDatasets = [];

    this.chartLabels = [];
    this.barChartData = new BarChartData();
    this.barChartData.data = [];
    this.barChartData.label = 'Sales Graphs';
    this.chartColors = new BarChartColors();
    this.chartColors.backgroundColors = [];
    this.chartColors.borderColors = [];
    this.chartColors.borderWidth = 2;
    this.barChartColors = [];
    this.dailyPurchasesReport = new DailyPurchasesReport();
    
    let days : number =  (this.endDate.getTime() - this.startDate.getTime()) / (24*60*60*1000);

  
    let dateTMP:Date;

    this.itemsSoldReport = [];

    for(var day=0; day<=days; day++ ){
     
      dateTMP = new Date(this.startDate.getTime() + day*24*60*60*1000);
      this.reportDate = datepipe.transform(dateTMP, 'YYYY-MM-dd')!;
      this.reportDateDay = datepipe.transform(dateTMP, 'EEEE')!;

      console.log(this.reportDate);
      console.log(day);
      let res: any = await this.reportService.getDailyPurchasesReport("/report/getItemsSoldReport?PurchaseDate=" + this.reportDate);
      //this.dailyPurchasesReport = res;
      //await this.generateItemsSoldReport(this.reportDate);
      //this.dailyPurchasesReport = res;

      this.itemsSoldReportTMP = res;
      // this.dataSource = this.dailyPurchasesReport.purchaseInvoices; // res.purchaseInvoices;

      this.itemsSoldReportTMP.forEach(
        rep =>{
          rep.parentCaption = this.reportDate;
          rep.day = this.reportDateDay;
          this.itemsSoldReport.push(rep);
        }
      );
      this.itemsSoldReportTMP  = [];
            
    }

    this.dataSource = [];

    this.totalPrice = 0;
    this.totalSales = 0;
    this.itemsSoldReport.forEach(
      isr =>{
        this.totalSales +=  isr.sales;
        this.totalPrice +=  isr.totalPrice;
      }
    );

    
    this.dataSource = this.itemsSoldReport;
  
    // this.dataSource = this.dailyPurchasesReport.purchaseInvoices; // res.purchaseInvoices;
    this.busy = false;
    
  }

  exportToExcel() {
    import("xlsx").then(xlsx => {
        const worksheet = xlsx.utils.json_to_sheet(this.itemsSoldReport); // Sale Data
        const workbook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
        const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
        this.saveAsExcelFile(excelBuffer, "AccountantReport");
    });
  }

  saveAsExcelFile(buffer: any, fileName: string): void {


    let EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
    let EXCEL_EXTENSION = ".xlsx";
    let fName = fileName + "_export_" + new Date().getTime() + EXCEL_EXTENSION;
    const data: Blob = new Blob([buffer], { type: EXCEL_TYPE });
    console.log(fName);
    FileSaver.saveAs(data, fName);

    // import("file-saver").then(FileSaver => {
    //   let EXCEL_TYPE =
    //     "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
    //   let EXCEL_EXTENSION = ".xlsx";
    //   const data: Blob = new Blob([buffer], {
    //     type: EXCEL_TYPE
    //   });
    //   // let tmp:string =  fileName + "_export_" + EXCEL_EXTENSION;
    //   // console.log(tmp);
    //   //TypeError: FileSaver.saveAs is not a function
    //   FileSaver.saveAs(
    //     data,
    //      fileName + "_export_" + new Date().getTime() + EXCEL_EXTENSION
    //   //  tmp
    //   );
    // });
  }
  

  async generateItemsSoldReport(reportDate:string) {
    let price: number = 0;
    let linkedNodeId: number = 0;
    this.totalPrice = 0;
    this.cashTotalPrice = 0;
    this.cardTotalPrice = 0;
    let caption: String = "";
    let sales: number = 0;
    this.totalSales = 0;
    this.cashTotalSales = 0;
    this.cardTotalSales = 0;
    let iDs: number[] = [];
    this.dataSource = [];
    this.itemsSoldReportList = [];

    await this.getMenus();
    this.dailyPurchasesReport.itemPurchaseDetails.forEach(
      ipd => {

        if (ipd.menuItem?.id != undefined) {
          if (!iDs.includes(ipd.menuItem?.id)) {
            iDs.push(ipd.menuItem?.id);
          }
        }
      }
    );

    let parrentNode: MenuNode | undefined;
    let newParrentNode: MenuNode;

    for (var id of iDs) {
      this.dailyPurchasesReport.itemPurchaseDetails.forEach(
        ipd => {
          if (ipd.menuItem?.id == id) {
            if (sales == 0) {
              caption = ipd.menuItem.caption;
              linkedNodeId = ipd.menuItem.linkedId
            }
            // if( ipd.menuItem.id != 6380 && ipd.menuItem.id != 6382 && ipd.menuItem.id != 6383 ){
            // price += ipd.menuItem.price * ipd.items;
            // this.subTotalPrice += ipd.menuItem.price * ipd.items;
            // this.totalPrice += ipd.menuItem.price * ipd.items;
            // } else 
            {
              price += ipd.price * ipd.items;
              this.subTotalPrice += ipd.price * ipd.items;
              this.totalPrice += ipd.price * ipd.items;
            }
           // this.totalPrice += ipd.menuItem.price * ipd.items;
            this.totalSales += ipd.items; //++;
           // this.subTotalPrice += ipd.menuItem.price * ipd.items;
            this.subTotal += ipd.items; //++;
            sales += ipd.items; //++;

          }
        }
      );

      let node: MenuNode = new MenuNode();
      node.id = id;
      node.linkedId = linkedNodeId;

      parrentNode = this.getParentNode(node);

      if (sales > 0) {
        let itemsSoldReport: ItemsSoldReport = new ItemsSoldReport();

        itemsSoldReport.parentCaption = reportDate;// parrentNode.menuItemCategory.description;
        itemsSoldReport.caption = caption;
        itemsSoldReport.sales = sales;
        itemsSoldReport.totalPrice = price;

        this.barChartData.data.push(sales);

        this.chartColors.backgroundColors.push('rgba(255, 99, 132, 0.2)');
        this.chartColors.borderColors.push('rgba(255,99,132,1)');
        sales = 0;
        price = 0;
        this.itemsSoldReportList.push(itemsSoldReport);
        this.chartLabels.push(caption);
      }
    }
    this.itemsSoldReportList.sort((a, b) => {
      return a.parentCaption.toString().localeCompare(b.parentCaption.toString());
    })

    let parentCaption: String = "";
    let oldParentCaption: String = "";
    this.subTotal = 0;
    this.subTotalPrice = 0;

    this.itemsSoldReportList.forEach(
      isr => {
       
        parentCaption = isr.parentCaption;
        if (oldParentCaption != "") {
          if (parentCaption != oldParentCaption) {
            let itemsSoldReport: ItemsSoldReport = new ItemsSoldReport();
            itemsSoldReport.parentCaption = oldParentCaption;
            itemsSoldReport.caption = "<<<<<<<" + oldParentCaption + " SUB TOTAL>>>>>>>";
            itemsSoldReport.sales = this.subTotal;
            itemsSoldReport.totalPrice = this.subTotalPrice;
            this.finalItemsSoldReportList.push(itemsSoldReport);
            this.subTotal = 0;
            this.subTotalPrice = 0;
          }
          
        }
        this.finalItemsSoldReportList.push(isr);
        this.subTotal += isr.sales;
        this.subTotalPrice += isr.totalPrice
 
         oldParentCaption = parentCaption;
      }
    );


        let itemsSoldReport: ItemsSoldReport = new ItemsSoldReport();
        itemsSoldReport.parentCaption = oldParentCaption;
        itemsSoldReport.caption = "<<<<<<<" + oldParentCaption + " SUB TOTAL>>>>>>>";
        itemsSoldReport.sales = this.subTotal;
        itemsSoldReport.totalPrice = this.subTotalPrice;
        this.finalItemsSoldReportList.push(itemsSoldReport);
        this.subTotal = 0;
        this.subTotalPrice = 0;


    this.finalItemsSoldReportList.sort((a, b) => {
      return a.parentCaption.toString().localeCompare(b.parentCaption.toString());
    })

    this.chartDatasets.push(this.barChartData);
    this.barChartColors.push(this.chartColors);

    // let totalRecord: ItemsSoldReport = new ItemsSoldReport();
    // totalRecord.parentCaption = "Grand Total";
    // totalRecord.caption = "TOTAL:";
    // totalRecord.sales = this.totalSales;
    // totalRecord.totalPrice = this.totalPrice;
    // this.finalItemsSoldReportList.push(totalRecord);

   // this.dataSource = this.finalItemsSoldReportList;
    //console.log(this.itemsSoldReportList);
  }

  getParentNode(node: MenuNode): MenuNode {

    let found: boolean = false;
    let parent: MenuNode = new MenuNode();
    for (var item of this.menuItems) {
      if (item.id == node.linkedId) {
        parent = item;
        found = true;
        break;
      }
      // if(mi.linkedId == node.id)
      // console.log(mi);
    }

    if (parent.linkedId != 0 && found) {
      let newNode: MenuNode = new MenuNode();
      newNode.linkedId = parent.linkedId;
      parent = this.getParentNode(newNode);
    }
    return parent;;
  }



  public chartType: string = 'horizontalBar';



  public chartOptions: any = {
    responsive: true
  };
  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }


}
