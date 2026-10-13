import { MenuItemOption } from "./MenuItemOption";

export interface MenuOptionTile{
    color: string;
    cols: number;
    rows: number;
    text: string;
    type: string;
    selectedId:number;
    menuItemOption: MenuItemOption;
}