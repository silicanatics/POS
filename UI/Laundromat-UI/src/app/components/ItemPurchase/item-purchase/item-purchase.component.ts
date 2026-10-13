import { DatePipe } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ItemPurchase } from 'src/app/entities/ItemPurchase';
import { ItemPurchaseDetail } from 'src/app/entities/ItemPurchaseDetail';
import { ItemPurchaseDetailOption } from 'src/app/entities/ItemPurchaseDetailOption';
import { PurchaseDetail } from 'src/app/entities/PurchaseDetail';
import { PurchaseType } from 'src/app/entities/PurchaseType';
import { ItemPurchaseService } from 'src/app/services/ItemPurchase/item-purchase.service';

@Component({
  selector: 'app-item-purchase',
  templateUrl: './item-purchase.component.html',
  styleUrls: ['./item-purchase.component.css']
})
export class ItemPurchaseComponent implements OnInit {

  itemPurchases: ItemPurchase[] = [];
  purchaseDetailList: PurchaseDetail[] = []
  itemPurchaseDetailList: ItemPurchaseDetail[] = [];
  itemPurchaseDetailOptionList: ItemPurchaseDetailOption[] = [];
  selectedPurchaseDetail!: PurchaseDetail;
  total!: number;
  selectedRow!:number;
  selectedRow2!:number;
  purchaseType!:PurchaseType;
  purchaseState!:number;
  dataLoading:boolean=false;
  msg!:string;
  @Output() updatePurchaseItemEvent = new EventEmitter<PurchaseDetail>();
  @Output() completePurchaseItemEvent = new EventEmitter<PurchaseDetail>();
  @Output() reprintEvent = new EventEmitter<PurchaseDetail>();

  constructor(private itemPurchaseService: ItemPurchaseService) { }

  ngOnInit(): void {
    this.purchaseType = new PurchaseType();
    this.getItemPurchases();
  }
  async getItemPurchases() {
    let data = await this.itemPurchaseService.getItemPurchase("/purchase/findPurchases?stateId=1")
    this.purchaseDetailList = data;
    if(this.purchaseDetailList.length > 0){
    this.purchaseDetailList[0].itemPurchase.purchaseDate;
    }
  }

  async refresh(){
    this.purchaseDetailList = [];
    this.dataLoading=true;
    this.purchaseDetailList = [];
   let date:Date = new Date();
    const datepipe: DatePipe = new DatePipe('en-ZA');
    let reportDate = datepipe.transform(date, 'YYYY/MM/dd');  

    let data = await this.itemPurchaseService.getItemPurchase("/purchase/findPurchasesByType?purchaseTypeId=" + this.purchaseType.id + "&date=" +reportDate);
    this.purchaseDetailList = data;
    console.log(this.purchaseDetailList);
    this.dataLoading=false;
  }

  optionsClick(event:any){
    switch (event.target.value) {
      case "option1":
        this.purchaseType.id = 3;
        break;
      case "option2":
        this.purchaseType.id = 2;
        break;
      case "option3":
        this.purchaseType.id = 1;
        break;

      default:
        break;
    }
  }

  getPurchaseDetails(purchaseDetail: PurchaseDetail) {
    this.total = 0;
    this.msg = "";
    this.purchaseState = purchaseDetail.itemPurchase.purchaseState.id;
    console.log(this.purchaseState);
    this.selectedPurchaseDetail = purchaseDetail;
   // this.selectedRow = this.selectedPurchaseDetail.id;
    this.itemPurchaseDetailList = purchaseDetail.itemPurchaseDetailList;

    this.itemPurchaseDetailOptionList = purchaseDetail.itemPurchaseDetailOptionList;
   
    this.getTotalPurchase();
    console.log(this.itemPurchaseDetailOptionList);
  }

  test(id:number){
    this.selectedRow = id;
  }

  //selectedRow2
  test2(id:number){
    this.selectedRow2 = id;
  }

  getTotalPurchase() {


    for (var det of this.itemPurchaseDetailList) {
      this.total += det.menuItem!.price * det.items;
      for (var opt of this.itemPurchaseDetailOptionList) {
        if (det.id == opt.itemPurchaseDetail.id) {
          if (opt.selected) {
            this.total += opt.itemOption.price * det.items;
          }
        }
      }
    }
  }

  updatePurch() {
    if(this.purchaseState == 2){
    this.msg = "This Purchase has been comcluded!!!"
    } else {
      this.msg = "";
    this.updatePurchaseItemEvent.emit(this.selectedPurchaseDetail);
    }
  }

  completePurch() {
    if(this.purchaseState == 2){
      this.msg = "This Purchase has been comcluded!!!"
    } else {
      this.msg = "";
      this.selectedPurchaseDetail.itemPurchase.purchaseState.id = 2;
    this.completePurchaseItemEvent.emit(this.selectedPurchaseDetail);
  }
  }

  reprint(){
    this.reprintEvent.emit(this.selectedPurchaseDetail);
  }
 

}
