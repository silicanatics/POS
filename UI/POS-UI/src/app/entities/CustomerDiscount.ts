import { Customer } from "./Customer";

export class CustomerDiscount{
    id!:number;
	customer!: Customer ;
	startDate!:Date;
	endDate!:Date;
	accumulatedAmount!:number;
	threasholdAmount!:number;
	discountPercent!:number;
	discount!:number;
	purchases!:number;
}