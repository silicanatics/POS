import { DatePipe } from '@angular/common';
import { getQueryPredicate } from '@angular/compiler/src/render3/view/util';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { AccountantReport } from 'src/app/entities/AccountantReport';
import { BarChartColors } from 'src/app/entities/BarChartColors';
import { BarChartData } from 'src/app/entities/BarChartData';
import { DailyPurchasesReport } from 'src/app/entities/DailyPurchasesReport';
import { ItemsSoldReport } from 'src/app/entities/ItemsSoldReport';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuNode } from 'src/app/entities/MenuNode';
import { PurchaseInvoice } from 'src/app/entities/PurchaseInvoice';
import { Report } from 'src/app/entities/Report';
import { MenuService } from 'src/app/services/menu/menu.service';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-items-sold',
  templateUrl: './items-sold.component.html',
  styleUrls: ['./items-sold.component.css']
})
export class ItemsSoldComponent implements OnInit {

  reports: Report[] = [];
  menuItems: MenuItem[] = [];
  selectedValue!: Report;
  dailyPurchasesReport!: DailyPurchasesReport;
  accountantReport!: AccountantReport[];
  displayedColumns: string[] = ['ParentCaption', 'Caption', 'Sales', 'TotalPrice'];
  dataSource!: ItemsSoldReport[];
  //date!: Date;
  itemsSoldReportList: ItemsSoldReport[] = [];
  finalItemsSoldReportList: ItemsSoldReport[] = [];

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

  @Input()
  date!: Date;

  ngOnChanges(changes: SimpleChanges) {
    console.log(changes.date.currentValue);
    if (changes.date.currentValue != undefined) {
      this.getMenus();
      this.getData();

    }
  }


  constructor(private reportService: ReportService, private menuService: MenuService,) { }

  ngOnInit(): void {
    this.reports = [{ id: 1, description: 'Items Sold Report' },
    { id: 2, description: 'Month Days Report' },
    { id: 3, description: 'Accountant Report' },]


  }

  test() {
    this.getData();
  }

  OnDateChange(date: Date) {
    this.date = new Date(date);
    console.log(this.date);
    this.getData();
  }

  changeClient(value: MatSelectChange) {
    console.log(value.value);

    if (value.value == 1) {
      this.generateItemsSoldReport();
    }
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


    const datepipe: DatePipe = new DatePipe('en-ZA');
    let reportDate = datepipe.transform(this.date, 'YYYY-MM-dd');
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
    let res: any = await this.reportService.getDailyPurchasesReport("/report/getDailyPurchasesReport?PurchaseDate=" + reportDate);
    
    this.dailyPurchasesReport = res;
    // this.dataSource = this.dailyPurchasesReport.purchaseInvoices; // res.purchaseInvoices;
    this.dataSource = [];
    this.generateItemsSoldReport();
  }

  // getGeneralItemPrice(itemPurchDetid:number){

  //    for(var itemPurch of this.dailyPurchasesReport.itemPurchases){

  //     this.dailyPurchasesReport.itemPurchaseDetails[0].menuItem.
  //     if(itemPurch.)
  //    }
  // }

  async generateItemsSoldReport() {
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

        itemsSoldReport.ParentCaption = parrentNode.menuItemCategory.description;
        itemsSoldReport.Caption = caption;
        itemsSoldReport.Sales = sales;
        itemsSoldReport.TotalPrice = price;

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
      return a.ParentCaption.toString().localeCompare(b.ParentCaption.toString());
    })

    let parentCaption: String = "";
    let oldParentCaption: String = "";
    this.subTotal = 0;
    this.subTotalPrice = 0;

    this.itemsSoldReportList.forEach(
      isr => {
       
        parentCaption = isr.ParentCaption;
        if (oldParentCaption != "") {
          if (parentCaption != oldParentCaption) {
            let itemsSoldReport: ItemsSoldReport = new ItemsSoldReport();
            itemsSoldReport.ParentCaption = oldParentCaption;
            itemsSoldReport.Caption = "<<<<<<<" + oldParentCaption + " SUB TOTAL>>>>>>>";
            itemsSoldReport.Sales = this.subTotal;
            itemsSoldReport.TotalPrice = this.subTotalPrice;
            this.finalItemsSoldReportList.push(itemsSoldReport);
            this.subTotal = 0;
            this.subTotalPrice = 0;
          }
          
        }
        this.finalItemsSoldReportList.push(isr);
        this.subTotal += isr.Sales;
        this.subTotalPrice += isr.TotalPrice
 
         oldParentCaption = parentCaption;
      }
    );


        let itemsSoldReport: ItemsSoldReport = new ItemsSoldReport();
        itemsSoldReport.ParentCaption = oldParentCaption;
        itemsSoldReport.Caption = "<<<<<<<" + oldParentCaption + " SUB TOTAL>>>>>>>";
        itemsSoldReport.Sales = this.subTotal;
        itemsSoldReport.TotalPrice = this.subTotalPrice;
        this.finalItemsSoldReportList.push(itemsSoldReport);
        this.subTotal = 0;
        this.subTotalPrice = 0;


    this.finalItemsSoldReportList.sort((a, b) => {
      return a.ParentCaption.toString().localeCompare(b.ParentCaption.toString());
    })

    this.chartDatasets.push(this.barChartData);
    this.barChartColors.push(this.chartColors);

    // let totalRecord: ItemsSoldReport = new ItemsSoldReport();
    // totalRecord.ParentCaption = "Grand Total";
    // totalRecord.Caption = "TOTAL:";
    // totalRecord.Sales = this.totalSales;
    // totalRecord.TotalPrice = this.totalPrice;
    // this.finalItemsSoldReportList.push(totalRecord);

    this.dataSource = this.finalItemsSoldReportList;
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
