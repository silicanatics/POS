import { MenuItem } from "./MenuItem";
import { MenuItemOption } from "./MenuItemOption";

export class SelectedItemPurchaseDetail{
    id!: number;
    items!:number;
    price!:number;
    menuItem!: MenuItem;
    menuItemOptions!: MenuItemOption[];
    row!:number;
}