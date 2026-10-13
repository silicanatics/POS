using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class ItemPurchase
    {
        public int id { get; set; }
        public PurchaseInvoice purchaseInvoice { get; set; }
        public DateTime purchaseDate { get; set; }
        public float amount { get; set; }
        public float amountTendered { get; set; }
        public float change_ { get; set; }
        public float deliveryCharge { get; set; }
        public Customer customer { get; set; }
        public PurchaseType purchaseType { get; set; }
        public PurchaseState purchaseState { get; set; }
        public Usr user { get; set; }
    }
}