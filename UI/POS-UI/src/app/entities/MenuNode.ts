import { MenuItemCategory } from "./MenuItemCategory";
import { MenuItemType } from "./MenuItemType";
import { NodeType } from "./NodeType";

export class MenuNode{
    id!: number;
    linkedId!: number;
    caption!: String;
    price!: number;
    barcode!:String;
    menuItemType!:MenuItemType;
    nodeType!:NodeType;
    menuLevel!: number;
    options!: boolean;
    children!: MenuNode[];
    menuItemCategory!:MenuItemCategory;
}