import { PaymentType } from "./PaymentType";

export class MachinePrinter{
      id!:number;
	  ipAddress!:string;
	  machineName!:string;
	  paymentType!:PaymentType;
	  printerName!:string;
	  kitchenPrinterName!:string;
	  clientFontSize!:number;
	  kitchenFontSize!:number;
}