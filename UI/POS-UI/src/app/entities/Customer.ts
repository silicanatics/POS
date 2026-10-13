import { DBEntity } from "./DBEntity";
import { EntityAddress } from "./EntityAddress";
import { EntityContact } from "./EntityContact";

export class Customer{
    id!: number ;
    dbEntity!: DBEntity ;
    customerId!: string ;
    customerAddresses!:Array<EntityAddress>;
    customerContacts!:Array<EntityContact>;
}