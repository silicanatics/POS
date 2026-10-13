using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class ItemOption
    {
        public int id { get; set; }
        public string description { get; set; }
        public float price { get; set; }
        public Boolean selected { get; set; }
    }
}