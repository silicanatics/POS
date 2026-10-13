import { Customer } from "./Customer";
import { CustomerDiscount } from "./CustomerDiscount";
import { PurchaseType } from "./PurchaseType";

export class SelectedCustomer{
    purchaseType!:PurchaseType;
    customer!:Customer;
    customerDiscount!:CustomerDiscount;
}