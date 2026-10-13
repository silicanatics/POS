import { DBEntity } from "./DBEntity";

export class Usr{
    id!:number;
    username!:string;
    password!:string;
    enabled!:boolean;
    dBEntity!:DBEntity;
    token!:string;
}