import { DatePipe } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { DeliveryCharge } from 'src/app/entities/DeliveryCharge';
import { ItemPurchase } from 'src/app/entities/ItemPurchase';
import { ItemPurchaseDetail } from 'src/app/entities/ItemPurchaseDetail';
import { ItemPurchaseDetailOption } from 'src/app/entities/ItemPurchaseDetailOption';
import { PaymentType } from 'src/app/entities/PaymentType';
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
  dcId!:number;
  total!: number;
  selectedRow!:number;
  selectedRow2!:number;
  purchaseType!:PurchaseType;
  purchaseState!:number;
  dataLoading:boolean=false;
  deliveryChargeList:DeliveryCharge[] = [];
  date!: Date;
  msg!:string;
  deliverycharge!:DeliveryCharge;
  paymentType!:PaymentType;
  paymentTypes!:PaymentType[];
  @Output() updatePurchaseItemEvent = new EventEmitter<PurchaseDetail>();
  @Output() completePurchaseItemEvent = new EventEmitter<PurchaseDetail>();
  @Output() reprintEvent = new EventEmitter<PurchaseDetail>();


  constructor(private itemPurchaseService: ItemPurchaseService) { }


  OnDateChange(date:Date){
    this.date = new Date(date);
    console.log(this.date);   
  }

  ngOnInit(): void {
    this.purchaseType = new PurchaseType();
    this.date = new Date();
   // this.getItemPurchases();
   this.getDeliveryChargeList();
   this.getPaymentTypes();
  }

  async getPaymentTypes(){
    let data = await this.itemPurchaseService.getItemPurchase("/purchase/getPaymentTypes");
    this.paymentTypes = data; 
  }



  async getDeliveryChargeList(){    
    let data = await this.itemPurchaseService.getItemPurchase("/purchase/getDeliveryChargeList");
    this.deliveryChargeList = data;
    console.log(this.deliveryChargeList);    
  }

  changePaymentType(){    
    this.selectedPurchaseDetail.itemPurchase.paymentType = this.paymentType;
  }

  changeDelCharge(e:any){
   
    let delChargeStr: string = e.target.value;
    console.log(delChargeStr.substring(delChargeStr.indexOf('--')+2));
    delChargeStr = delChargeStr.substring(delChargeStr.indexOf('--')+2);
    let delCharge:number = this.deliverycharge.charge;
    console.log(delCharge);
    console.log(this.deliverycharge);

    this.selectedPurchaseDetail.itemPurchase.amount -= this.selectedPurchaseDetail.itemPurchase.deliveryCharge;
    this.total -= this.selectedPurchaseDetail.itemPurchase.deliveryCharge;
    this.selectedPurchaseDetail.itemPurchase.deliveryCharge = 0;
    this.selectedPurchaseDetail.itemPurchase.deliveryCharge =delCharge;
    this.selectedPurchaseDetail.itemPurchase.amount += delCharge;
    this.selectedPurchaseDetail.itemPurchase.amountTendered = this.selectedPurchaseDetail.itemPurchase.amount;
    this.selectedPurchaseDetail.itemPurchase.change_ = this.selectedPurchaseDetail.itemPurchase.amountTendered -
                                                       this.selectedPurchaseDetail.itemPurchase.amount;
    this.total += delCharge;
  }

  async getItemPurchases() {
    let data = await this.itemPurchaseService.getItemPurchase("/purchase/findPurchases?stateId=1")
    this.purchaseDetailList = data;
    this.purchaseDetailList[0].itemPurchase.purchaseInvoice.purchaseNumber
  }

  async refresh(){
    this.purchaseDetailList = [];
    this.dataLoading=true;
    this.purchaseDetailList = [];
  // this.date = new Date();
    const datepipe: DatePipe = new DatePipe('en-ZA');
    let reportDate = datepipe.transform(this.date, 'YYYY/MM/dd');  

    let data = await this.itemPurchaseService.getItemPurchase("/purchase/findPurchasesByType?purchaseTypeId=" + this.purchaseType.id + "&date=" +reportDate);
    this.purchaseDetailList = data;
    this.updateGeneralItems();
    console.log(this.purchaseDetailList);
    this.dataLoading=false;
  }

  updateGeneralItems(){
    this.purchaseDetailList.forEach(
      pdl =>{
        pdl.itemPurchaseDetailList.forEach(
          ipdl => {
            if(ipdl.menuItem?.price == 0){
              ipdl.menuItem.price = ipdl.price;
            }
          }
        );
      }
    );
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
    if(purchaseDetail.itemPurchase.paymentType == null){
      this.paymentType = this.paymentTypes.filter(pt => pt.id == 3)[0];
      purchaseDetail.itemPurchase.paymentType = this.paymentType;
    } else {
      this.paymentType = purchaseDetail.itemPurchase.paymentType;
    }
    this.selectedPurchaseDetail = purchaseDetail;
   // this.selectedRow = this.selectedPurchaseDetail.id;
    this.itemPurchaseDetailList = purchaseDetail.itemPurchaseDetailList;

    this.itemPurchaseDetailOptionList = purchaseDetail.itemPurchaseDetailOptionList;
    this.getTotalPurchase();    
  
    this.deliveryChargeList.forEach(
      dc => {
        if(dc.charge == purchaseDetail.itemPurchase.deliveryCharge){
          this.deliverycharge = dc;
        }
      }
    );

  }

  test(id:number){
    this.selectedRow = id;
  }

  //selectedRow2
  test2(id:number){
    this.selectedRow2 = id;
  }

  getTotalPurchase() {
    this.selectedPurchaseDetail.itemPurchase.change_ = 0 ; 
    //this.selectedPurchaseDetail.itemPurchase.amountTendered -     this.selectedPurchaseDetail.itemPurchase.amount;
    this.selectedPurchaseDetail.itemPurchase.amount = this.selectedPurchaseDetail.itemPurchase.amount +
    this.selectedPurchaseDetail.itemPurchase.deliveryCharge;
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
    this.total += this.selectedPurchaseDetail.itemPurchase.deliveryCharge;
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
