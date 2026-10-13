using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class Application
    {
        public int id { get; set; }
        public string name { get; set; }
        public int menuTileCols { get; set; }
        public string menuTileRowHeight { get; set; }
        public DateTime licenseExpireryDate { get; set; }
        public int daysToExpirery { get; set; }
        public bool licenseExpired { get; set; }

    }
}