import { ItemOption } from "./ItemOption";
import { ItemPurchaseDetail } from "./ItemPurchaseDetail";
import { MenuItemOption } from "./MenuItemOption";

export class ItemPurchaseDetailOption{
    id!:number;
    selected!:boolean;
    itemPurchaseDetail!: ItemPurchaseDetail;
    itemOption!:ItemOption;
    menuItemOption!:MenuItemOption;
    
}