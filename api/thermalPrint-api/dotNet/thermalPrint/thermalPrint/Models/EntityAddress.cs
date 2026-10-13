using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class EntityAddress
    {
        public int id { get; set; }
        public DBEntity dbEntity { get; set; }
        public string addressText { get; set; }
        public string line1 { get; set; }
        public string line2 { get; set; }
        public string line3 { get; set; }
        public string line4 { get; set; }
        public int postalCode { get; set; }
    }
}