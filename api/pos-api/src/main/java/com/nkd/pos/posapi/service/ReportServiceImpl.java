package com.nkd.pos.posapi.service;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.nkd.pos.posapi.entity.ClientPrinter;
import com.nkd.pos.posapi.entity.DBEntity;
import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.ItemsSoldReport;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.MenuItemOption;
import com.nkd.pos.posapi.entity.PrinterDestination;
import com.nkd.pos.posapi.entity.PurchasePayload;
import com.nkd.pos.posapi.entity.PurchaseState;
import com.nkd.pos.posapi.entity.SelectedItemPurchaseDetail;
import com.nkd.pos.posapi.entity.UserPrinter;
import com.nkd.pos.posapi.entity.Usr;
import com.nkd.pos.posapi.entityReports.ChartReport;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	EntityManager entityManager;
	@Autowired
	UserPrinterService userPrinterService;
	@Autowired
	ClientPrinterService clientPrinterService;
	@Autowired
	PrinterDestinationService printerDestinationService;

	List<MenuItem> allMenuItems;

	private static Font CB = FontFactory.getFont(FontFactory.TIMES_ROMAN, 39L, BaseColor.BLACK);

	private Logger logger = Logger.getLogger(getClass().getName());
	private static boolean jobRunning = true;
	private static String printer1 = "";

	@Value("${prn.printer_1}")
	public void setPrinter(String printer) {
		ReportServiceImpl.printer1 = printer;
	}

	private void test() {
		PrinterService printerService = new PrinterService();

		System.out.println(printerService.getPrinters());

		// print some stuff
		printerService.printString("XP-80C", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		// cut that paper!
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		printerService.printBytes(printer1, cutP);
	}

	@Override
	public void printTest(byte[] pdfbyte) {

		// this.test();

		InputStream bais = new ByteArrayInputStream(pdfbyte);
		InputStream bais2 = new BufferedInputStream(bais);
		FileInputStream is = null;

		try {
			File file = ResourceUtils.getFile("classpath:TextMerge.pdf");
			is = new FileInputStream(file);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("printTest...");

		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService prn : services) {
			logger.info(prn.getName());
		}

		PrintService selectedService = null;
		if (services.length != 0 || services != null) {
			for (PrintService service : services) {
				String existingPrinter = service.getName();
				if (existingPrinter.equals(ReportServiceImpl.printer1)) {
//		    	Arrays.stream(service.getSupportedDocFlavors()).forEach(f->System.out.println(f.getMediaType() + ":" + f.getMimeType() + ":" + f.getRepresentationClassName()));
					selectedService = service;
					logger.info("existing !!!!!!!!!!!!" + existingPrinter);
					break;
				}
			}
		}

		DocPrintJob printJob = selectedService.createPrintJob();
		printJob.addPrintJobListener(new JobCompleteMonitor());

		DocAttributeSet das = new HashDocAttributeSet();

		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A6);
		aset.add(new Copies(1));
		Doc doc = null;

		try {

			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

			pras.add(new Copies(1));
			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

			doc = new SimpleDoc(bais2, flavor, null);
			printJob.print(doc, null);
//			  while (jobRunning) {
//				  
//				    try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				 
//				  }
			try {
				bais.close();
				bais2.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static class JobCompleteMonitor extends PrintJobAdapter {
		@Override
		public void printJobCompleted(PrintJobEvent jobEvent) {
			System.out.println("Job completed");
			jobRunning = false;
		}
	}

	@Override
	public byte[] getInvoiceImage(PurchasePayload purchasePayload) throws IOException, IOException {

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		GateBookHeaderFooterPageEvent event = new GateBookHeaderFooterPageEvent();
		try {
			Rectangle pageSize = new Rectangle(PageSize.A6);
			// pageSize.setBackgroundColor(new BaseColor(255, 253, 208));
			final Document invoiceDoc = new Document(pageSize);
			PdfWriter writer = PdfWriter.getInstance(invoiceDoc, baos);
			// writer.setPageEvent(event);
			invoiceDoc.open();
			handleInvoicebject(invoiceDoc, purchasePayload);
			// printTest(baos);
			invoiceDoc.close();

			// printReciept(purchasePayload);

			// cutPaper();

			// cut that paper!

			return baos.toByteArray();

		} catch (DocumentException e) {

			e.printStackTrace();
		}

		return null;

	}

	private void cutPaper() {
		PrinterService printerService = new PrinterService();

		// System.out.println(printerService.getPrinters());

		// print some stuff
		printerService.printString(printer1, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		// cut that paper!
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		printerService.printBytes(printer1, cutP);
	}

	private void cutKitchenPaper(String printer) {
		PrinterService printerService = new PrinterService();

		// System.out.println(printerService.getPrinters());

		// print some stuff
		printerService.printString(printer, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		// cut that paper!
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		printerService.printBytes(printer, cutP);
	}

	private void cutClientPaper(String printer) {
		PrinterService printerService = new PrinterService();

		// System.out.println(printerService.getPrinters());

		// print some stuff
		printerService.printString(printer, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		// cut that paper!
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		printerService.printBytes(printer, cutP);
	}

	private EntityAddress getUserAddress(Usr user) {
		EntityAddress entityAddress = null;

		// DBEntity

		String qlString = "select ea " + "from Usr u inner join DBEntity e " + "on u.dBEntity.id = e.id "
				+ "inner join EntityAddress ea " + "on ea.dbEntity.id = e.id " + "where u.id =  " + user.getId();
		List<EntityAddress> entityAddressList = entityManager.createQuery(qlString, EntityAddress.class)
				.getResultList();

		return entityAddressList.get(0);
	}

	@Override
	public void printReciept(PurchasePayload purchasePayload) {

		String tillPrinter = null;
		String kitchenPrinter = null;
		String ipAddress = null;

		ipAddress = purchasePayload.getClientIP();

		List<ClientPrinter> clientPrinterList = clientPrinterService.findAll();
		int user_id = purchasePayload.getUser().getId();
		List<UserPrinter> userPrinterList = userPrinterService.findAll();

		List<PrinterDestination> printerDestinationList = printerDestinationService.findAll();

		for (ClientPrinter clientPrinter : clientPrinterList) {

			if (clientPrinter.getIpAddress().equals(ipAddress)) {
				tillPrinter = clientPrinter.getPrinterName();
				break;
			}
		}

		if (tillPrinter == null) {
			for (UserPrinter usrP : userPrinterList) {

				if (usrP.getUser().getId() == user_id) {
					if (usrP.getPrinterDestination().getId() == 2) {
						tillPrinter = usrP.getPrinterName();
					}

				}
			}
		}

		for (PrinterDestination printerDestination : printerDestinationList) {

			if (printerDestination.getId() == 1) {
				kitchenPrinter = printerDestination.getDefaultPrinter();
			}
		}

		if (hasKitchenFood(purchasePayload)) {

			//printKitchenInvoice(kitchenPrinter, purchasePayload);
		}
		
		if (tillPrinter != null) {

			printInvoiceClient(tillPrinter, purchasePayload);
		}

//			try {
//				printKitchenInvoiceBytes(kitchenPrinter, purchasePayload);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//			PageFormat format = new PageFormat();
//	        Paper paper = new Paper();
//
//	        double paperWidth = 3;//3.25
//	        double paperHeight = 3.69;//11.69
//	        double leftMargin = 0.12;
//	        double rightMargin = 0.10;
//	        double topMargin = 0;
//	        double bottomMargin = 0.01;
//	        
//	        paper.setSize(paperWidth * 200, paperHeight * 200);
//	        paper.setImageableArea(leftMargin * 200, topMargin * 200,
//	                (paperWidth - leftMargin - rightMargin) * 200,
//	                (paperHeight - topMargin - bottomMargin) * 200);
//
//	        format.setPaper(paper);
//	        
//	        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//	        aset.add(OrientationRequested.PORTRAIT);
//	        
//	        PrinterJob printerJob = PrinterJob.getPrinterJob();
//	        
//	        Printable printable = new PrinterService();
//	       
//	        format = printerJob.validatePage(format);
//	        boolean don = printerJob.printDialog();
//	        printerJob.setPrintable(printable, format);
//	        
//	        try {
//	            printerJob.print(aset);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }

		//}

		// printInvoiceClient(tillPrinter, purchasePayload);

	}

	boolean hasKitchenFood(PurchasePayload purchasePayload) {

		boolean found = false;

		for (SelectedItemPurchaseDetail spi : purchasePayload.getSelectedItemPurchaseDetails()) {
			if (spi.getMenuItem().getMenuItemType().getId() == 1) {
				found = true;
				break;
			}
		}

		return found;
	}

	private void printInvoiceClient(String printer, PurchasePayload purchasePayload) {

		PrinterService printerService = new PrinterService();

		System.out.println(printerService.getPrinters());

		String printLoad = "";

		printLoad += "Invoice Date: " + purchasePayload.getItemPurchase().getPurchaseDate().toLocalDate();
		printLoad += "\n";
		printLoad += "------------------------";
		printLoad += "\n";

		printLoad += "# " + purchasePayload.getItemPurchase().getPurchaseInvoice().getPurchaseNumber();
		printLoad += "\n";
		printLoad += "------------------------";
		printLoad += "\n";

		////// Get Address

		EntityAddress entityAdress = this.getUserAddress(purchasePayload.getUser());

		printLoad += entityAdress.getAddressText();
		printLoad += "\n";
		printLoad += "-------------------------------------------------------";
		printLoad += "\n";

		Iterator<SelectedItemPurchaseDetail> selectedItemPurchaseDetailIter = purchasePayload
				.getSelectedItemPurchaseDetails().iterator();
		SelectedItemPurchaseDetail sipd = null;

		while (selectedItemPurchaseDetailIter.hasNext()) {

			sipd = selectedItemPurchaseDetailIter.next();

			printLoad += sipd.getMenuItem().getItems() + " " + sipd.getMenuItem().getCaption() + "\t\t" + " R"
					+ String.format("%.2f", sipd.getMenuItem().getPrice());

			if (sipd.getMenuItemOptions() != null) {
				printLoad += "\n";
				Iterator<MenuItemOption> menuItemOptionIter = sipd.getMenuItemOptions().iterator();
				MenuItemOption mio = null;
				while (menuItemOptionIter.hasNext()) {
					String option = null;
					mio = menuItemOptionIter.next();
					if (mio.getItemOption().isSelected() && mio.isDefaultOption())
						continue;
					if (!mio.getItemOption().isSelected() && !mio.isDefaultOption())
						continue;
					if (mio.isDefaultOption()) {
						option = "No " + mio.getItemOption().getDescription();
					} else {
						option = mio.getItemOption().getDescription();
					}

					printLoad += "  ==>  " + option + "\t"
							+ (mio.getItemOption().getPrice() > 0
									? "R" + String.format("%.2f", mio.getItemOption().getPrice())
									: "");
					printLoad += "\n";

				}

			}

			if (selectedItemPurchaseDetailIter.hasNext()) {

			}

		}

		printLoad += "-------------------------------------------------------";

		printLoad += "\n";

		printLoad += "Total: " + "\t\t\t" + "R" + String.format("%.2f", purchasePayload.getItemPurchase().getAmount());
		printLoad += "\n";
		printLoad += "Amount Tendered: " + "\t" + "R"
				+ String.format("%.2f", purchasePayload.getItemPurchase().getAmountTendered());
		printLoad += "\n";
		printLoad += "Change: " + "\t\t" + "R" + String.format("%.2f", purchasePayload.getItemPurchase().getChange_());

//print some stuff
		printerService.printString(printer, printLoad);

		// cut that paper!
		// byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		// printerService.printBytes("XP-80C", cutP);

		cutClientPaper(printer);

	}

	private void printKitchenInvoice(String printer, PurchasePayload purchasePayload) {

		int menuItemTypeId = 0;
		PrinterService printerService = new PrinterService();

		System.out.println(printerService.getPrinters());

		String printLoad = "";
		printLoad += "\n";
		printLoad += "\n";
		printLoad += "\n";
		printLoad += "\n";
		printLoad += "\n";
		printLoad += "Kitchen: " + purchasePayload.getItemPurchase().getPurchaseDate().toLocalDate();
		printLoad += "\n";
		printLoad += "------------------------";
		printLoad += "\n";

		printLoad += "Order# " + purchasePayload.getItemPurchase().getPurchaseInvoice().getPurchaseNumber();
		printLoad += "\n";
		printLoad += "------------------------";
		printLoad += "\n";

		Iterator<SelectedItemPurchaseDetail> selectedItemPurchaseDetailIter = purchasePayload
				.getSelectedItemPurchaseDetails().iterator();
		SelectedItemPurchaseDetail sipd = null;

		while (selectedItemPurchaseDetailIter.hasNext()) {

			sipd = selectedItemPurchaseDetailIter.next();
			menuItemTypeId = sipd.getMenuItem().getMenuItemType().getId();
			if (menuItemTypeId != 1) {
				continue;
			}

			for (int i = 0; i < sipd.getMenuItem().getItems(); i++) {

				// printLoad += sipd.getMenuItem().getItems() + " " +
				// sipd.getMenuItem().getCaption();
				printLoad += sipd.getMenuItem().getCaption();
				if (sipd.getMenuItemOptions() != null) {
					printLoad += "\n";
					Iterator<MenuItemOption> menuItemOptionIter = sipd.getMenuItemOptions().iterator();
					MenuItemOption mio = null;
					while (menuItemOptionIter.hasNext()) {
						String option = null;
						mio = menuItemOptionIter.next();
						if (mio.getItemOption().isSelected() && mio.isDefaultOption())
							continue;
						if (!mio.getItemOption().isSelected() && !mio.isDefaultOption())
							continue;
						if (mio.isDefaultOption()) {
							option = "No " + mio.getItemOption().getDescription();
						} else {
							option = mio.getItemOption().getDescription();
						}

						printLoad += "  ==>  " + option;

						printLoad += "\n";

					}

				}
			}

			if (selectedItemPurchaseDetailIter.hasNext()) {

			}

		}

		printLoad += "-------------------------------------------------------";

		printLoad += "\n";

		printerService.printString(printer, printLoad);

		// cut that paper!
		// byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		// printerService.printBytes("XP-80C", cutP);

		cutKitchenPaper(printer);

	}

	private void printKitchenInvoiceBytes(String printer, PurchasePayload purchasePayload) throws IOException {

		PrinterService printerService = new PrinterService();

		byte[] bytes = getInvoiceImage(purchasePayload);

		printerService.printPDFBytes(printer, bytes);

	}

	void handleInvoicebject(Document invoiceDoc, PurchasePayload purchasePayload) {

		Paragraph paragraph = null;
		Chunk chunk = null;
		float[] columnWidths = null;
		PdfPCell c1 = null;
		PdfPCell cell = null;
		Phrase phrase = null;

		try {

			// --*************************************************Data

			logger.info("handleInvoicebject");

			// --*************************************************************

			try {
				Image imageData = Image.getInstance("classpath:/logo.png");
				// imageData.setAbsolutePosition(300f, 650f);
				imageData.setAlignment(Image.ALIGN_LEFT);
				// invoiceDoc.add(imageData);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			float lineSpacing = 2.5F;
			Font font = new Font(FontFamily.COURIER, 10);

			chunk = new Chunk("Invoice Date: " + purchasePayload.getItemPurchase().getPurchaseDate().toLocalDate(),
					font);
			phrase = new Phrase();
			phrase.add(chunk);
			paragraph = new Paragraph();
			paragraph.add(phrase);
			paragraph.setFont(CB);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			invoiceDoc.add(paragraph);

			chunk = new Chunk("-------------------------------------------------------");
			phrase = new Phrase();
			phrase.add(chunk);
			paragraph = new Paragraph();
			paragraph.add(phrase);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(CB);
			invoiceDoc.add(paragraph);

			// invoiceDoc.add(Chunk.NEWLINE);

//			chunk = new Chunk("-------------------");
//			phrase = new Phrase();
//			phrase.add(chunk);
//			paragraph = new Paragraph();
//			paragraph.add(phrase);
//			paragraph.setAlignment(Element.ALIGN_LEFT);
//			paragraph.setFont(CB);
//			invoiceDoc.add(paragraph);

			Iterator<SelectedItemPurchaseDetail> selectedItemPurchaseDetailIter = purchasePayload
					.getSelectedItemPurchaseDetails().iterator();
			SelectedItemPurchaseDetail sipd = null;

			PdfPTable table = new PdfPTable(3);
			table.setHorizontalAlignment(0);
			PdfPCell tCcell = null;
			table.setTotalWidth(new float[] { 10, 120, 40 });
			table.setLockedWidth(true);
			while (selectedItemPurchaseDetailIter.hasNext()) {

				sipd = selectedItemPurchaseDetailIter.next();

				tCcell = new PdfPCell(new Phrase("1", font));
				// tCcell.setFixedHeight(30);
				tCcell.setBorder(Rectangle.NO_BORDER);
				// tCcell.setColspan(2);
				table.addCell(tCcell);
//		        
				// logger.info(sipd.getMenuItem().getCaption());
				tCcell = new PdfPCell(new Phrase(sipd.getMenuItem().getCaption(), font));
				// tCcell.setFixedHeight(30);
				tCcell.setBorder(Rectangle.NO_BORDER);
				// tCcell.setColspan(2);
				table.addCell(tCcell);

				tCcell = new PdfPCell(new Phrase("R" + sipd.getMenuItem().getPrice(), font));
				// tCcell.setFixedHeight(30);
				tCcell.setBorder(Rectangle.NO_BORDER);
				// tCcell.setColspan(2);
				table.addCell(tCcell);

				if (sipd.getMenuItemOptions() != null) {

					Iterator<MenuItemOption> menuItemOptionIter = sipd.getMenuItemOptions().iterator();
					MenuItemOption mio = null;
					while (menuItemOptionIter.hasNext()) {
						String option = null;
						mio = menuItemOptionIter.next();
						if (mio.getItemOption().isSelected() && mio.isDefaultOption())
							continue;
						if (!mio.getItemOption().isSelected() && !mio.isDefaultOption())
							continue;
						if (mio.isDefaultOption()) {
							option = "No " + mio.getItemOption().getDescription();
						} else {
							option = mio.getItemOption().getDescription();
						}

						tCcell = new PdfPCell(new Phrase("", font));
						// tCcell.setFixedHeight(30);
						tCcell.setBorder(Rectangle.NO_BORDER);
						// tCcell.setColspan(2);
						table.addCell(tCcell);
//					        
						// logger.info(sipd.getMenuItem().getCaption());
						tCcell = new PdfPCell(new Phrase(option, font));
						// tCcell.setFixedHeight(30);
						tCcell.setBorder(Rectangle.NO_BORDER);
						// tCcell.setColspan(2);
						table.addCell(tCcell);

						tCcell = new PdfPCell(new Phrase("", font));
						// tCcell.setFixedHeight(30);
						tCcell.setBorder(Rectangle.NO_BORDER);
						// tCcell.setColspan(2);
						table.addCell(tCcell);

//						chunk = new Chunk("   " + option);
//						phrase = new Phrase();
//						phrase.add(chunk);
//						paragraph = new Paragraph();
//						paragraph.add(phrase);
//						paragraph.setAlignment(Element.ALIGN_LEFT);
//						paragraph.setFont(CB);
//						invoiceDoc.add(paragraph);
					}

				}

				if (selectedItemPurchaseDetailIter.hasNext()) {
					// invoiceDoc.add(Chunk.NEWLINE);
				}

			}

			invoiceDoc.add(table);

			chunk = new Chunk("-------------------------------------------------------");
			phrase = new Phrase();
			phrase.add(chunk);
			paragraph = new Paragraph();
			paragraph.add(phrase);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(CB);
			invoiceDoc.add(paragraph);

			chunk = new Chunk("Total: R" + purchasePayload.getItemPurchase().getAmount(), font);
			phrase = new Phrase();
			phrase.add(chunk);
			paragraph = new Paragraph();
			paragraph.add(phrase);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(CB);
			invoiceDoc.add(paragraph);

			chunk = new Chunk("Amount Tendered: R" + purchasePayload.getItemPurchase().getAmountTendered(), font);
			phrase = new Phrase();
			phrase.add(chunk);
			paragraph = new Paragraph();
			paragraph.add(phrase);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(CB);
			invoiceDoc.add(paragraph);

			chunk = new Chunk("Change: R" + purchasePayload.getItemPurchase().getChange_(), font);
			phrase = new Phrase();
			phrase.add(chunk);
			paragraph = new Paragraph();
			paragraph.add(phrase);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(CB);
			invoiceDoc.add(paragraph);

		} catch (DocumentException e) {

			e.printStackTrace();
		}

	}

	class GateBookHeaderFooterPageEvent extends PdfPageEventHelper {

		public void onStartPage(PdfWriter writer, Document document) {

			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase("Gate Book Register"), 500, 800, 0);
		}

	}

	@Override
	public byte[] getItemsSoldReport(String date) {

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		GateBookHeaderFooterPageEvent event = new GateBookHeaderFooterPageEvent();
////
//		String qlString = "select mi.caption, sum(ipd.items),  sum(ipd.items*mi.price) "
//				+ "from ItemPurchase it inner join ItemPurchaseDetail ipd on it.id = ipd.itemPurchase.id "
//				+ "inner join MenuItem mi on ipd.menuItem.id = mi.id " + " group by mi.caption";
		if (date == "indefined") {
			date = "2000-01-01";
		}
		String qlString = "select mi.caption , sum(ipd.items) as sales,  sum(mi.price*ipd.items) as totalPrice "
				+ "from ItemPurchase it inner join ItemPurchaseDetail ipd on it.id = ipd.itemPurchase.id "
				+ "inner join PurchaseInvoice pi on pi.invoiceNumber = it.purchaseInvoice.invoiceNumber "
				+ "inner join MenuItem mi on ipd.menuItem.id = mi.id " + "where pi.invoiceDate = '" + date + "'"
				+ " group by mi.caption";

		try {
			Rectangle pageSize = new Rectangle(PageSize.A4);
			// pageSize.setBackgroundColor(new BaseColor(255, 253, 208));
			final Document reportDoc = new Document(pageSize);
			PdfWriter writer = PdfWriter.getInstance(reportDoc, baos);
			// writer.setPageEvent(event);

			Query q = entityManager.createQuery(qlString);
			@SuppressWarnings("unchecked")
			List<Object[]> itemsSoldReportList = (List<Object[]>) q.getResultList();
			// List<ItemsSoldReport> itemsSoldReportList =
			// entityManager.createQuery(qlString, ItemsSoldReport.class).getResultList();

			reportDoc.open();
			handleItemsSoldReportListObject(reportDoc, itemsSoldReportList);
			// printTest(baos);
			reportDoc.close();

			// printReciept(purchasePayload);

			// cutPaper();

			// cut that paper!

			return baos.toByteArray();

		} catch (DocumentException e) {

			e.printStackTrace();
		}

		return null;
	}

	void handleItemsSoldReportListObject(Document reportDoc, List<Object[]> itemsSoldReportList) {

		float lineSpacing = 2.5F;
		Font fontHeader = new Font(FontFamily.TIMES_ROMAN, 15);
		Font font = new Font(FontFamily.COURIER, 10);

		try {
			Iterator<Object[]> itemsSoldReportListIter = itemsSoldReportList.iterator();
			Object[] itemsSoldReport = null;
			PdfPTable table = new PdfPTable(3);
			// table.setHorizontalAlignment(0);
			PdfPCell tCcell = null;
			table.setTotalWidth(new float[] { 170, 60, 120 });
			// table.setLockedWidth(true);

			tCcell = new PdfPCell(new Phrase("Caption", fontHeader));
			tCcell.setBackgroundColor(BaseColor.YELLOW);
			table.addCell(tCcell);
			tCcell = new PdfPCell(new Phrase("Sales", fontHeader));
			tCcell.setBackgroundColor(BaseColor.YELLOW);
			table.addCell(tCcell);
			tCcell = new PdfPCell(new Phrase("Total Price", fontHeader));
			tCcell.setBackgroundColor(BaseColor.YELLOW);
			table.addCell(tCcell);

			while (itemsSoldReportListIter.hasNext()) {

				itemsSoldReport = itemsSoldReportListIter.next();

				String caption = itemsSoldReport[0].toString();
				tCcell = new PdfPCell(new Phrase(caption, font));
				// tCcell.setFixedHeight(30);
				// tCcell.setBorder(Rectangle.NO_BORDER);
				// tCcell.setColspan(2);
				table.addCell(tCcell);
//	        
				// logger.info(sipd.getMenuItem().getCaption());
				String sales = itemsSoldReport[1].toString();
				tCcell = new PdfPCell(new Phrase(sales, font));
				// tCcell.setFixedHeight(30);
				// tCcell.setBorder(Rectangle.NO_BORDER);
				// tCcell.setColspan(2);
				table.addCell(tCcell);

				String totalPrice = itemsSoldReport[2].toString();
				tCcell = new PdfPCell(new Phrase("R" + totalPrice, font));
				// tCcell.setFixedHeight(30);
				// tCcell.setBorder(Rectangle.NO_BORDER);
				// tCcell.setColspan(2);
				table.addCell(tCcell);

				logger.info(caption + " " + sales + " " + totalPrice);

				if (itemsSoldReportListIter.hasNext()) {
					// invoiceDoc.add(Chunk.NEWLINE);
				}

			}
			reportDoc.add(table);

		} catch (DocumentException e) {

			e.printStackTrace();
		}

	}

	@Override
	public List<ChartReport> getCharts(int menuId, String strDate) {

		List<ChartReport> chartsList = new ArrayList<ChartReport>();

		LocalDate reportDate = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		PurchaseState purchaseState = null;

		String date = reportDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		String qlString = "select mi from MenuItem mi";
		List<MenuItem> menuItemList = entityManager.createQuery(qlString, MenuItem.class).getResultList();

		if (menuId == 0) {
			this.allMenuItems = menuItemList;
		} else {
			this.allMenuItems = new ArrayList<MenuItem>();
			this.accumulateAllChildren(menuItemList, menuId);
		}
		qlString = "select pd from ItemPurchaseDetail pd inner join ItemPurchase ip "
				+ " on pd.itemPurchase.id = ip.id "
				+ " inner join PurchaseInvoice pi on pi.invoiceNumber = ip.purchaseInvoice.invoiceNumber "
				+ " where pi.invoiceDate = '" + date + "'";
		List<ItemPurchaseDetail> itemPurchaseDetailList = entityManager.createQuery(qlString, ItemPurchaseDetail.class)
				.getResultList();

		float price = 0;

		for (MenuItem mi : this.allMenuItems) {
			price = 0;
			for (ItemPurchaseDetail ipd : itemPurchaseDetailList) {
				if (mi.getId() == ipd.getMenuItem().getId()) {
					price += ipd.getPrice() * ipd.getItems();
				}
			}
			if (price > 0) {
				ChartReport cr = new ChartReport();
				cr.setMi(mi);
				cr.setValue(price);
				cr.setDescription(mi.getCaption());
				chartsList.add(cr);
			}
		}

		return chartsList;
	}

	private void accumulateAllChildren(List<MenuItem> menuItemList, int menuId) {

		List<MenuItem> ret = null;

		for (MenuItem mi : menuItemList) {

			if (mi.getLinkedId() == menuId) {
				this.allMenuItems.add(mi);
				accumulateAllChildren(menuItemList, mi.getId());
			}
		}

	}

}
