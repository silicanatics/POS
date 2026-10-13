import { ItemPurchase } from "./ItemPurchase";
import { MenuItem } from "./MenuItem";

export class ItemPurchaseDetail {
    id: number | undefined;
    menuItem: MenuItem | undefined;
    items!:number;
    price!:number;
    itemPurchase!:ItemPurchase;
}