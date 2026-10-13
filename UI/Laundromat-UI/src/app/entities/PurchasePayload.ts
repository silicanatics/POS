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
    printerName!:string;
    itemPurchase!:ItemPurchase;
    selectedItemPurchaseDetails!: SelectedItemPurchaseDetail[];
}