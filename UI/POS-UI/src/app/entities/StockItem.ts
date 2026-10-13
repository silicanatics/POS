import { MenuItem } from "./MenuItem";
import { QuantityType } from "./QuantityType";

export class StockItem{
    id!:number;
    caption!: string;
    menuItem!:MenuItem;
    totalQuantity!:number;
    quantityType!:QuantityType;
}