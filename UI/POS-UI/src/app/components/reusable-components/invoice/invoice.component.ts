import { ElementRef, Output, SimpleChanges, ViewChild } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Application } from 'src/app/entities/Application';
import { CustomerDiscount } from 'src/app/entities/CustomerDiscount';
import { MachinePrinter } from 'src/app/entities/MachinePrinter';
import { PaymentType } from 'src/app/entities/PaymentType';
import { PurchasePayload } from 'src/app/entities/PurchasePayload';
import { Tile } from 'src/app/entities/Tile';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {

  digits: string = "0";
  amountTendered!: number;
  @Input()
  purchaseAmount!: number;
  @Input()
  invoiceImage!: any;
  @Input()
  machinePrinters!: MachinePrinter[];
  @Input()
  purchasePayload!: PurchasePayload;
  @Input()
  customerDiscountInput!: CustomerDiscount;
  customerDiscount!: CustomerDiscount;
  @Output() getAmountEntered = new EventEmitter<number>();
  @Output() paymentType = new EventEmitter<PaymentType>();
  @Output() processPayment = new EventEmitter<number>();

  @ViewChild('btnprint', { static: false })
  btnPrint!: ElementRef;
  accumulatedAmount: number = 0;
  threasholdAmount: number = 0;
  discountPercent: number = 0;
  purchases: number = 0;
  application!: Application;
  discount: boolean = false;
  discountAmount: number = 0;

  CashProcess: boolean = true;
  CardProcess: boolean = false;
  Change: number = 0;
  Proceed: boolean = false;

  tiles: Tile[] = [

    { text: 'input', cols: 3, rows: 1, color: 'lightblue', type: 'input' },

    { text: '', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '<<', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '', cols: 1, rows: 1, color: 'lightblue', type: 'button' },

    { text: '1', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '2', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '3', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '4', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '5', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '7', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '8', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '9', cols: 1, rows: 1, color: 'lightblue', type: 'button' },

    { text: '', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '0', cols: 1, rows: 1, color: 'lightblue', type: 'button' },
    { text: '.', cols: 1, rows: 1, color: 'lightblue', type: 'button' },

  ];

  //// R{{customerDiscount!.accumulatedAmount}}


  ngOnChanges(changes: SimpleChanges) {

    if (this.application != null) {
      if (this.application.id == 3) {
        if (changes.customerDiscountInput.currentValue != null) {
          this.customerDiscount = this.customerDiscountInput;
          this.accumulatedAmount = this.customerDiscount.accumulatedAmount;
          this.threasholdAmount = this.customerDiscount.threasholdAmount;
          this.discountPercent = this.customerDiscount.discountPercent;
          this.purchases = this.customerDiscount.purchases
          if (this.accumulatedAmount + this.purchaseAmount >= 1000) {
            this.discountAmount = this.purchaseAmount * 0.1;
            this.discount = true;
          }
          console.log("*****************************");
          console.log(this.customerDiscount);
        }
      }
    }
  }

  ProcessTypeClick(process: string) {

    if (process == "Cash") {
      this.digits = "0";
      this.CashProcess = true;
      this.CardProcess = false;
      if (this.Proceed == false) {
        if (this.Change > 0) {
          this.Proceed = true;
        }
        else {
          this.Proceed = false;
        }
      }
      this.purchasePayload.itemPurchase.amountTendered = 0;
      this.purchasePayload.itemPurchase.change_ = this.purchaseAmount * -1;
    } else if (process == "Card") {

      this.digits = <string><unknown>this.purchaseAmount.toFixed(2);
      this.amountTendered = this.purchaseAmount;
      this.CardProcess = true;
      this.CashProcess = false;
      this.Change = this.amountTendered - this.purchaseAmount;
      this.Proceed = true;
      this.purchasePayload.itemPurchase.amountTendered = this.amountTendered;
      this.purchasePayload.itemPurchase.change_ = 0;
      this.getAmountEntered.emit(this.amountTendered);
    }
    this.Proceed = (this.amountTendered + this.discountAmount >= this.purchaseAmount);

  }
  constructor() { }

  ngOnInit(): void {
    let app: string = localStorage.getItem('application')!;
    this.application = JSON.parse(app);

    this.customerDiscount = new CustomerDiscount();

  }

  ItemPurchase(digit: string) {
    switch (digit) {
      case '0':
        if (this.digits.length > 0) {
          if (this.digits.length == 1 && this.digits == '0') {
            //Do nothing!!!
          } else {
            this.digits = this.digits + digit;
          }
        }
        break;
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        if (this.digits.includes('.')) {

          if (this.digits.substring(this.digits.indexOf('.')).length > 2) {
            return;
          }
        }
        if (this.digits.length == 1 && this.digits == '0') {
          this.digits = '';
        }
        this.digits = this.digits + digit;
        break;
      case '.':
        if (!this.digits.includes('.')) {
          this.digits = this.digits + digit;
        }
        break;
      case 'C':
        this.digits = '';
        break;
      case '<<':
        this.digits = this.digits.substring(0, this.digits.length - 1);
        console.log(this.digits.substring(this.digits.length - 1));
        if (this.digits.substring(this.digits.length - 1) == '.') {
          this.digits = this.digits.substring(0, this.digits.length - 1);
        }
        if (this.digits.length == 0) {
          this.digits = '0';
        }
        break;
      default:
        break;
    }
    console.log(this.amountTendered);
    this.amountTendered = Number(this.digits);
    this.getAmountEntered.emit(this.amountTendered + this.discountAmount);
    this.Change = this.amountTendered + this.discountAmount - this.purchaseAmount;
    this.purchasePayload.itemPurchase.amountTendered = this.amountTendered + this.discountAmount;
    this.purchasePayload.itemPurchase.change_ = this.Change;
    if (this.Change >= 0) {
      this.Proceed = true;
    }
    else {
      this.Proceed = false;
    }
  }

  clearSPID() {
    //this.selectedItemPurchaseDetails = []
    this.purchasePayload.itemPurchase.purchaseInvoice.invoiceNumber;

    console.log(this.purchasePayload.selectedItemPurchaseDetails);
  }

  ProcessPurchase() {
 
    if (this.purchaseAmount == 0)
      return;



    let cashPrinterFound: boolean = false;
    let cardPrinterFound: boolean = false;

    this.machinePrinters.forEach(
      mp => {
        if (mp.paymentType.id == 1) {
          if (mp.printerName.length > 0) {
            cashPrinterFound = true;
          }
        }
        if (mp.paymentType.id == 2) {
          if (mp.printerName.length > 0) {
            cardPrinterFound = true;
          }
        }
      }
    );

    const element = document.getElementById('btnprint');
    const element2 = document.getElementById('btnprint2');
    if (element) {
      if (this.CashProcess && !cashPrinterFound) {

        element.click();
      }
    }

    if (element2) {
      if (this.CardProcess && !cardPrinterFound) {

        element2.click();
      }
    }

    let paymentType: PaymentType = new PaymentType();

    if (this.CashProcess) {
      paymentType.id = 1;
    } else {
      paymentType.id = 2;
    }

    this.paymentType.emit(paymentType);
    this.processPayment.emit(this.amountTendered);

    this.accumulatedAmount = 0;
    this.threasholdAmount = 0;
    this.discountPercent = 0;
    this.purchases = 0;
  }

}
