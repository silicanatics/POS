using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Cors;
using System.Drawing.Printing;
using System.Drawing;
using thermalPrint.Models;

namespace thermalPrint.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class thermalPrintController : ApiController
    {
        PurchasePayload purchasePayload;

        [HttpPost]
        public PurchasePayload printInvoice([FromBody] PurchasePayload _purchasePayload)
        {
            purchasePayload = _purchasePayload;

            if(_purchasePayload.kitchenPrinterName == "")
            {
                _purchasePayload.kitchenPrinterName = null;
            }
            if (_purchasePayload.clientPrinterName != null)
            {
                
                purchasePayload.clientIP = "fsdfs!!!!!!!!!!!";
                PrintDocument printClient = new PrintDocument();
                printClient.PrintPage += Pd_PrintClientPage;
                PrinterSettings clientPrinterSettings = new PrinterSettings();
                clientPrinterSettings.PrinterName = _purchasePayload.clientPrinterName;
                printClient.PrinterSettings = clientPrinterSettings;
                printClient.Print();
            }

            if (_purchasePayload.kitchenPrinterName != null)
            {

                Boolean hasKitchenItems = false;
                foreach (SelectedItemPurchaseDetail sipd in purchasePayload.selectedItemPurchaseDetails)
                {
                    if (sipd.menuItem.menuItemType.id == 1)
                    {
                        hasKitchenItems = true;
                        break;
                    }
                }

                if (hasKitchenItems)
                {
                    purchasePayload.clientIP = "fsdfs!!!!!!!!!!!";
                    PrintDocument printKitchen = new PrintDocument();
                    printKitchen.PrintPage += Pd_PrintKitchenPage;
                    PrinterSettings kitchenPrinterSettings = new PrinterSettings();
                    kitchenPrinterSettings.PrinterName = _purchasePayload.kitchenPrinterName;
                    printKitchen.PrinterSettings = kitchenPrinterSettings;
                    printKitchen.Print();
                }
            }



            return purchasePayload;
        }

        private void Pd_PrintClientPage(object sender, PrintPageEventArgs e)
        {
            int kitchenFont = 10;
            if (purchasePayload.clientFontSize != 0)
            {
                kitchenFont = purchasePayload.clientFontSize;
            }
            Font headingFint = new System.Drawing.Font("Arial", 10, FontStyle.Regular);
            Font pencilFont = new System.Drawing.Font("Arial", kitchenFont, FontStyle.Regular);
            Font QuantityFont1 = new System.Drawing.Font("Arial", kitchenFont, FontStyle.Bold);
            Font ReportTotalFont = new System.Drawing.Font("Arial", kitchenFont, FontStyle.Bold);

            SolidBrush pencil = new SolidBrush(Color.Black);
            float xPos = 0;
            float yPos = 0;

            float linesPerPage = 0;
            float leftMargin = e.MarginBounds.Left;
            float topMargin = e.MarginBounds.Top;
            int count = 1;
            int chipOptionRow = 0;
            int chickOptionRow = 0;
            int pizzaoptionRow = 0;
            int achaoptionRow = 0;
            int saladRow = 0;
            int mastardRow = 0;
            string[] addressArray;

            DateTime dt = DateTime.Now;
            String date = dt.ToLongDateString() + "@" + dt.ToLongTimeString();
            e.Graphics.DrawString(date, headingFint, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);


            //String time = dt.ToLongTimeString();
            //e.Graphics.DrawString("Time: " + time, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            //count++;
            //yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);


            if (purchasePayload.user.entityAddress.line1 != null)
            {
                e.Graphics.DrawString(purchasePayload.user.entityAddress.line1, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }

            if (purchasePayload.user.entityAddress.line2 != null)
            {
                e.Graphics.DrawString(purchasePayload.user.entityAddress.line2, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }

            if (purchasePayload.user.entityAddress.line3 != null)
            {
                e.Graphics.DrawString(purchasePayload.user.entityAddress.line3, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }

            if (purchasePayload.user.entityAddress.line4 != null)
            {
                e.Graphics.DrawString(purchasePayload.user.entityAddress.line4, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }


            e.Graphics.DrawString(" ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            if (purchasePayload.application.id != 4)
            {
                e.Graphics.DrawString("Order: " + purchasePayload.itemPurchase.purchaseInvoice.purchaseNumber.ToString(), QuantityFont1, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }

            e.Graphics.DrawString("---------------------------------------------------- ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

          

            foreach (SelectedItemPurchaseDetail sipd in purchasePayload.selectedItemPurchaseDetails)
            {
                string item = "";
                item = sipd.menuItem.items.ToString() + "==>" + sipd.menuItem.caption + " R" + sipd.menuItem.price;
                e.Graphics.DrawString(item, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

                foreach (MenuItemOption mio in sipd.menuItemOptions)
                {
                    string option = "";

                    if (mio.defaultOption)
                    {
                        if (!mio.itemOption.selected)
                        {
                            option = "No " + mio.itemOption.description;

                            e.Graphics.DrawString(option, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
                            count++;
                            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                        }
                    } else
                    {
                        if (mio.itemOption.selected)
                        {
                            option =  mio.itemOption.description;
                            if( mio.itemOption.price > 0)
                            {
                                option += " R" +mio.itemOption.price;
                            }
                            e.Graphics.DrawString(option, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
                            count++;
                            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                        }

                    
                    }

         
                }
                  


            }

            e.Graphics.DrawString("---------------------------------------------------- ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            if (purchasePayload.itemPurchase.purchaseType.id != 1)
            {
                e.Graphics.DrawString(purchasePayload.customer.dbEntity.name, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);


                foreach (var cc in purchasePayload.customer.customerContacts)
                {
                    e.Graphics.DrawString(cc.contact, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                    count++;
                    yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

                }

                foreach (var ca in purchasePayload.customer.customerAddresses)
                {
                    e.Graphics.DrawString(ca.addressText, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                    count++;
                    yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                }

                string deliveryCharge = "Delivery Charge: R" + purchasePayload.itemPurchase.deliveryCharge.ToString();
                e.Graphics.DrawString(deliveryCharge, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }

            string total = "";
            total = "Total: R" + purchasePayload.itemPurchase.amount;
            e.Graphics.DrawString(total, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            string amountTendered = "";
            amountTendered = "Amount Tendered: R" + purchasePayload.itemPurchase.amountTendered;
            e.Graphics.DrawString(amountTendered, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            string change = "";
            change = "Change: R" + purchasePayload.itemPurchase.change_;
            e.Graphics.DrawString(change, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            //string paymentType = "";
            //paymentType = "Payment Type: " + purchasePayload.itemPurchase.pa .description;
            //e.Graphics.DrawString(paymentType, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
            //count++;
            //yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            e.Graphics.DrawString("  ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            e.Graphics.DrawString("Thank You. ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            //throw new NotImplementedException();
        }

        private void Pd_PrintKitchenPage(object sender, PrintPageEventArgs e)
        {
 

            int kitchenFont = 10;
            if (purchasePayload.kitchenFontSize  != 0)
            {
                kitchenFont = purchasePayload.kitchenFontSize;
            }

            Boolean hasKitchenItems = false;
            foreach (SelectedItemPurchaseDetail sipd in purchasePayload.selectedItemPurchaseDetails)
            {
                if (sipd.menuItem.menuItemType.id == 1)
                {
                    hasKitchenItems = true;
                    break;
                }
            }

            Font headingFint = new System.Drawing.Font("Arial", 10, FontStyle.Regular);
            Font pencilFont = new System.Drawing.Font("Arial", kitchenFont, FontStyle.Regular);
            Font QuantityFont1 = new System.Drawing.Font("Arial", kitchenFont, FontStyle.Bold);
            Font ReportTotalFont = new System.Drawing.Font("Arial", kitchenFont, FontStyle.Bold);

            SolidBrush pencil = new SolidBrush(Color.Black);
            float xPos = 0;
            float yPos = 0;

            float linesPerPage = 0;
            float leftMargin = e.MarginBounds.Left;
            float topMargin = e.MarginBounds.Top;
            int count = 1;
            int chipOptionRow = 0;
            int chickOptionRow = 0;
            int pizzaoptionRow = 0;
            int achaoptionRow = 0;
            int saladRow = 0;
            int mastardRow = 0;
            string[] addressArray;


            DateTime dt = DateTime.Now;
            String date = dt.ToLongDateString() + "@" + dt.ToLongTimeString();
            e.Graphics.DrawString(date, headingFint, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);


            //String time = dt.ToLongTimeString();
            //e.Graphics.DrawString("Time: " + time, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            //count++;
            //yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            if(purchasePayload.itemPurchase.purchaseType.id != 1)
            {               
                e.Graphics.DrawString(purchasePayload.itemPurchase.purchaseType.description, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }
            

            if (hasKitchenItems)
            {
                e.Graphics.DrawString("Kitchen", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }
            
            e.Graphics.DrawString(" ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);


            e.Graphics.DrawString("Order: " + purchasePayload.itemPurchase.purchaseInvoice.purchaseNumber.ToString(), QuantityFont1, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            e.Graphics.DrawString("---------------------------------------------------- ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);



            foreach (SelectedItemPurchaseDetail sipd in purchasePayload.selectedItemPurchaseDetails)
            {
                if (sipd.menuItem.menuItemType.id != 1)
                    continue;
                string item = "";
                item = sipd.menuItem.items.ToString() + "==>" + sipd.menuItem.caption + " R" + sipd.menuItem.price;
                e.Graphics.DrawString(item, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

                foreach (MenuItemOption mio in sipd.menuItemOptions)
                {
                    string option = "";

                    if (mio.defaultOption)
                    {
                        if (!mio.itemOption.selected)
                        {
                            option = "No " + mio.itemOption.description;

                            e.Graphics.DrawString(option, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
                            count++;
                            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                        }
                    }
                    else
                    {
                        if (mio.itemOption.selected)
                        {
                            option = mio.itemOption.description;
                            if (mio.itemOption.price > 0)
                            {
                                option += " R" + mio.itemOption.price;
                            }
                            e.Graphics.DrawString(option, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
                            count++;
                            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                        }


                    }


                }



            }

            if (hasKitchenItems)
            {

                e.Graphics.DrawString("----------------Other Items-------------------------", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            }

            foreach (SelectedItemPurchaseDetail sipd in purchasePayload.selectedItemPurchaseDetails)
            {
                if (sipd.menuItem.menuItemType.id == 1)
                    continue;
                string item = "";
                item = sipd.menuItem.items.ToString() + "==>" + sipd.menuItem.caption + " R" + sipd.menuItem.price;
                e.Graphics.DrawString(item, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

                foreach (MenuItemOption mio in sipd.menuItemOptions)
                {
                    string option = "";

                    if (mio.defaultOption)
                    {
                        if (!mio.itemOption.selected)
                        {
                            option = "No " + mio.itemOption.description;

                            e.Graphics.DrawString(option, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
                            count++;
                            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                        }
                    }
                    else
                    {
                        if (mio.itemOption.selected)
                        {
                            option = mio.itemOption.description;
                            if (mio.itemOption.price > 0)
                            {
                                option += " R" + mio.itemOption.price;
                            }
                            e.Graphics.DrawString(option, pencilFont, new SolidBrush(Color.Blue), 15, yPos);
                            count++;
                            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                        }


                    }


                }



            }


            e.Graphics.DrawString("---------------------------------------------------- ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

            if (purchasePayload.itemPurchase.purchaseType.id != 1)
            {
                e.Graphics.DrawString(purchasePayload.customer.dbEntity.name, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);


                foreach (var cc in purchasePayload.customer.customerContacts)
                {
                    e.Graphics.DrawString(cc.contact, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                    count++;
                    yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

                }

                foreach (var ca in purchasePayload.customer.customerAddresses)
                {
                    e.Graphics.DrawString(ca.addressText, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                    count++;
                    yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
                }

                string deliveryCharge = "Delivery Charge: R" + purchasePayload.itemPurchase.deliveryCharge.ToString();
                e.Graphics.DrawString(deliveryCharge, pencilFont, new SolidBrush(Color.Blue), 5, yPos);
                count++;
                yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);
            }




            e.Graphics.DrawString("---------------------------------------------------- ", pencilFont, new SolidBrush(Color.Blue), 5, yPos);
            count++;
            yPos = topMargin + count * pencilFont.GetHeight(e.Graphics);

          
        }

    }
}
