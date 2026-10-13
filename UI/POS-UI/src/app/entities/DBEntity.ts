import { EntityType } from "./EntityType";

export class DBEntity{
    id!:number;
    name!:string;
    surname!:string;
    initials!:string;
    entityType!:EntityType;
}