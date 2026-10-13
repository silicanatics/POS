using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace thermalPrint.Models
{
    public class Customer
    {
        public int id { get; set; }


        public DBEntity dbEntity { get; set; }
        public string customerId { get; set; }
        public List<EntityAddress> customerAddresses { get; set; }
        public List<EntityContact> customerContacts { get; set; }
    }
}