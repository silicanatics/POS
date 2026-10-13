using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class MenuItem
    {
        public int id { get; set; }

        public int linkedId { get; set; }
        public string caption { get; set; }
        public double price { get; set; }
        public double vat { get; set; }
        public int menuLevel { get; set; }
        public int items { get; set; }
        public MenuItemType menuItemType { get; set; }
    }
}