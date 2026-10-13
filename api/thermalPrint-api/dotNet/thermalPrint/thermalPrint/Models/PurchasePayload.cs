using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class PurchasePayload
    {
        public int EmployeeId { get; set; }
        public int id { get; set; }
        public Usr user { get; set; }
        public string clientIP { get; set; }
        public Customer customer { get; set; }
        private PurchaseType purchaseType { get; set; }
        public string clientPrinterName { get; set; }
        public string kitchenPrinterName { get; set; }
        public int clientFontSize { get; set; }
        public int kitchenFontSize { get; set; }
        public ItemPurchase itemPurchase { get; set; }
        public List<SelectedItemPurchaseDetail> selectedItemPurchaseDetails { get; set; }
        public Application application { get; set; }
    }
}