import { DBEntity } from "./DBEntity";

export class EntityAddress{
    id!:number;
    dbEntity!:DBEntity;
    addressText!:string;
    line1!:string;
    line2!:string;
    line3!:string;
    line4!:string;
    postalCode!:number;
}