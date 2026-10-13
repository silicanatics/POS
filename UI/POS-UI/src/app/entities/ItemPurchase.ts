import { Customer } from "./Customer";
import { PaymentType } from "./PaymentType";
import { PurchaseInvoice } from "./PurchaseInvoice";
import { PurchaseState } from "./PurchaseState";
import { PurchaseType } from "./PurchaseType";
import { Usr } from "./Usr";




export class ItemPurchase {
    id!: number;
    purchaseDate!: string;
    amount!: number;
    amountTendered!: number;
    change_!: number;
    deliveryCharge!:number;
    paymentType!:PaymentType;
    customer!: Customer;
    purchaseType!:PurchaseType;
    purchaseInvoice!:PurchaseInvoice;
    purchaseState!:PurchaseState;
    user!:Usr;
  //  user!:User;
}