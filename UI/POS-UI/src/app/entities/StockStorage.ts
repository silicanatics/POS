import { StockItem } from "./StockItem";

export class StockStorage{

	id!:number;
    stockItem!:  StockItem  ;	
	dateCaptured!: Date;
	startDate!: Date ;
	initialQuantity!: number ;
	endDate!: Date ;
	soldQuantity!: number ;
	quantityLeft!: number ;
	current!: boolean ;
	price!: number ;
	itemCostPrice!: number ;
	itemResellPrice!: number ;
	profit!: number ;
	sequence!: number ;

}