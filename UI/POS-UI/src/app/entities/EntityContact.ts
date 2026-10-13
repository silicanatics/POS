import { ContactType } from "./ContactType";
import { DBEntity } from "./DBEntity";

export class EntityContact{
    id!:number;
    dbEntity!:DBEntity;
    contactType!:ContactType;
    contact!:string;
}