using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class Usr
    {

        public int id { get; set; }
        public DBEntity dBEntity { get; set; }
        public EntityAddress entityAddress { get; set; }
        public string userId { get; set; }
        public string username { get; set; }
        public string password { get; set; }
        public Boolean enabled { get; set; }
        public string roles { get; set; }
    }
}