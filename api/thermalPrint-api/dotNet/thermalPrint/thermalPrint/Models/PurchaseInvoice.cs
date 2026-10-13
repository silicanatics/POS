using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class PurchaseInvoice
    { 
        public long invoiceNumber { get; set; }
        public int purchaseNumber { get; set; }
        public DateTime invoiceDate { get; set; }
        public DateTime invoiceDateTime { get; set; }
    }
}