import { ItemPurchase } from "./ItemPurchase";
import { ItemPurchaseDetail } from "./ItemPurchaseDetail";
import { ItemPurchaseDetailOption } from "./ItemPurchaseDetailOption";
import { PurchaseInvoice } from "./PurchaseInvoice";

export class DailyPurchasesReport{
    reportDate!:string;
    purchaseInvoices!:PurchaseInvoice[];
    itemPurchases!:ItemPurchase[];
    itemPurchaseDetails!:ItemPurchaseDetail[];
    itemPurchaseDetailOptions!:ItemPurchaseDetailOption[];

}