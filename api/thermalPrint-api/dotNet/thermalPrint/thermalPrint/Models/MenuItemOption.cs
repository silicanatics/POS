using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class MenuItemOption
    {
        public int id { get; set; }
        public Boolean defaultOption { get; set; }
        public ItemOption itemOption { get; set; }
        public MenuItem menuItem { get; set; }
    }
}