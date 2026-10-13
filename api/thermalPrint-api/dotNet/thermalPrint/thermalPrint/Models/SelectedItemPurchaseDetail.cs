using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class SelectedItemPurchaseDetail
    {
        public int id { get; set; }
        public MenuItem menuItem { get; set; }
        public List<MenuItemOption> menuItemOptions { get; set; }
    }
}