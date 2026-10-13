import { ItemPurchase } from "./ItemPurchase";
import { ItemPurchaseDetail } from "./ItemPurchaseDetail";
import { ItemPurchaseDetailOption } from "./ItemPurchaseDetailOption";

export class PurchaseDetail{
    id!:number;
    itemPurchase!:ItemPurchase;
    itemPurchaseDetailList!:Array<ItemPurchaseDetail>;
    itemPurchaseDetailOptionList!:Array<ItemPurchaseDetailOption>;
}