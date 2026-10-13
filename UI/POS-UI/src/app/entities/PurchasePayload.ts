import { Application } from "./Application";
import { Customer } from "./Customer";
import { ItemPurchase } from "./ItemPurchase";
import { ItemPurchaseDetail } from "./ItemPurchaseDetail";
import { ItemPurchaseDetailOption } from "./ItemPurchaseDetailOption";
import { PurchaseType } from "./PurchaseType";
import { SelectedItemPurchaseDetail } from "./SelectedItemPurchaseDetail";
import { Usr } from "./Usr";

export class PurchasePayload{
    user!:Usr;
    customer!:Customer;
    purchaseType!:PurchaseType;
    clientIP!:string;
    clientPrinterName!:string;
    kitchenPrinterName!:string;
    clientFontSize!:number;
    kitchenFontSize!:number;
    itemPurchase!:ItemPurchase;
    selectedItemPurchaseDetails!: SelectedItemPurchaseDetail[];
    application! : Application;
}