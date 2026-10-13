import { MenuItem } from "./MenuItem";

export interface MenuTile{
    color: string;
    cols: number;
    rows: number;
    text: string;
    type: string;
    menuItem: MenuItem;
}