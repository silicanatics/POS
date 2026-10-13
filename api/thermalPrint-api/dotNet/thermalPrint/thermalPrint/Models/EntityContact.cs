using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class EntityContact
    {
        public int id { get; set; }
        public DBEntity dbEntity { get; set; }
        public ContactType contactType { get; set; }
        public String contact { get; set; }
    }
}