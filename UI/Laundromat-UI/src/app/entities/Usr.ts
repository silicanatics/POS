import { DBEntity } from "./DBEntity";
import { EntityAddress } from "./EntityAddress";

export class Usr{
    id!:number;
    username!:string;
    password!:string;
    enabled!:boolean;
    dBEntity!:DBEntity;
    entityAddress!:EntityAddress;
    token!:string;
}