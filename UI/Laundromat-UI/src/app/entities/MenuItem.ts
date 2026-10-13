import { MenuItemCategory } from "./MenuItemCategory";
import { MenuItemType } from "./MenuItemType";
import { NodeType } from "./NodeType";

export class MenuItem{
     id!: number;
     items!: number;
     linkedId!: number;
     caption!: String;
     unitPrice!:number;
     price!: number;
     barcode!:String;
     icon!:String;
     menuLevel!: number;
     nodeType!:NodeType;
     menuItemType!:MenuItemType;
     options!: boolean;
     children!: MenuItem[];
     menuItemCategory!:MenuItemCategory;

}