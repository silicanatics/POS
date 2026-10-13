package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.controller.CustomerController;
import com.nkd.pos.posapi.entity.Application;
import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.ItemPurchaseDetailOption;
import com.nkd.pos.posapi.entity.ItemsSoldReport;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.PurchaseInvoice;
import com.nkd.pos.posapi.entityReports.AccountantReport;
import com.nkd.pos.posapi.entityReports.DailyPurchasesReport;

@Service
public class EntityReportsServiceImpl implements EntityReportsService {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	EntityManager entityManager;
	
	MenuItem  previousMenuItem = new MenuItem();
	Application application = null;
	

	
	@Override
	public AccountantReport getAccountantReport(String startDate, String endDate) {
	
		String qlString;
		AccountantReport report = new AccountantReport();
		LocalDate reportStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate reportEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		report.setStartDate(reportStartDate);
		report.setEndDate(reportEndDate);
		
		qlString = "select p from PurchaseInvoice p where invoiceDate >= '" + startDate + "' and invoiceDate <= '" + endDate + "'";
		List<PurchaseInvoice> purchaseInvoiceList = entityManager
				.createQuery(qlString,PurchaseInvoice.class).getResultList();
		
		qlString = "select ip from PurchaseInvoice p inner join ItemPurchase ip " +
		           " on ip.purchaseInvoice.id = p.id " +
				   "where p.invoiceDate >= '" + startDate + "' and p.invoiceDate <= '" + endDate + "'";
		List<ItemPurchase> itemPurchaseList = entityManager
				.createQuery(qlString,ItemPurchase.class).getResultList();
		
		qlString = "select ipd from PurchaseInvoice p inner join ItemPurchase ip " +
		           " on ip.purchaseInvoice.id = p.id " +
				   " inner join ItemPurchaseDetail ipd " +
		           " on ipd.itemPurchase.id = ip.id " +
				   "where p.invoiceDate >= '" + startDate + "' and p.invoiceDate <= '" + endDate + "'";
		List<ItemPurchaseDetail> itemPurchaseDetailList = entityManager
				.createQuery(qlString,ItemPurchaseDetail.class).getResultList();
		
		
		qlString = "select ipdo from PurchaseInvoice p inner join ItemPurchase ip " +
		           " on ip.purchaseInvoice.id = p.id " +
				   " inner join ItemPurchaseDetail ipd " +
		           " on ipd.itemPurchase.id = ip.id " +
				   " inner join ItemPurchaseDetailOption ipdo " +
		           " on ipdo.itemPurchaseDetail.id = ipd.id " +
				   "where p.invoiceDate >= '" + startDate + "' and p.invoiceDate <= '" + endDate + "'";
		List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList = entityManager
				.createQuery(qlString,ItemPurchaseDetailOption.class).getResultList();
		
		report.setItemPurchaseDetailOptions(itemPurchaseDetailOptionList);
		report.setItemPurchaseDetails(itemPurchaseDetailList);
		report.setItemPurchases(itemPurchaseList);
		report.setPurchaseInvoices(purchaseInvoiceList);
		return report;
		
	}
	
	
	@Override
	public DailyPurchasesReport getDailyPurchaseReport(String date) {
		String qlString;
		DailyPurchasesReport report = new DailyPurchasesReport();
		LocalDate reportDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		report.setReportDate(reportDate);
	
		qlString = "select p from PurchaseInvoice p where invoiceDate = '" + date + "'";
		List<PurchaseInvoice> purchaseInvoiceList = entityManager
				.createQuery(qlString,PurchaseInvoice.class).getResultList();
		
		qlString = "select ip from PurchaseInvoice p inner join ItemPurchase ip " +
		           " on ip.purchaseInvoice.id = p.id " +
				   "where p.invoiceDate = '" + date + "'";
		List<ItemPurchase> itemPurchaseList = entityManager
				.createQuery(qlString,ItemPurchase.class).getResultList();
		
		qlString = "select ipd from PurchaseInvoice p inner join ItemPurchase ip " +
		           " on ip.purchaseInvoice.id = p.id " +
				   " inner join ItemPurchaseDetail ipd " +
		           " on ipd.itemPurchase.id = ip.id " +
				   "where p.invoiceDate = '" + date + "'";
		List<ItemPurchaseDetail> itemPurchaseDetailList = entityManager
				.createQuery(qlString,ItemPurchaseDetail.class).getResultList();
		
		
		qlString = "select ipdo from PurchaseInvoice p inner join ItemPurchase ip " +
		           " on ip.purchaseInvoice.id = p.id " +
				   " inner join ItemPurchaseDetail ipd " +
		           " on ipd.itemPurchase.id = ip.id " +
				   " inner join ItemPurchaseDetailOption ipdo " +
		           " on ipdo.itemPurchaseDetail.id = ipd.id " +
				   "where p.invoiceDate = '" + date + "'";
		List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList = entityManager
				.createQuery(qlString,ItemPurchaseDetailOption.class).getResultList();
		
		report.setItemPurchaseDetailOptions(itemPurchaseDetailOptionList);
		report.setItemPurchaseDetails(itemPurchaseDetailList);
		report.setItemPurchases(itemPurchaseList);
		report.setPurchaseInvoices(purchaseInvoiceList);
		return report;
	}


	@Override
	public List<ItemsSoldReport> getItemsSoldReport(String date) {
		
		float sales;
		double totalPrice;
		
	
		
		String qlString = "select a from Application a ";
		application = entityManager
					.createQuery(qlString,Application.class).getSingleResult();
		
		
		qlString = "select mi from MenuItem mi ";
		List<MenuItem> menuItemList = entityManager
				.createQuery(qlString,MenuItem.class).getResultList();
		
		List<ItemsSoldReport> itemsSoldReportList = new ArrayList<ItemsSoldReport>();
	
		List<MenuItem> miListTMP = new ArrayList<MenuItem>();
		DailyPurchasesReport dailyPurchasesReport = this.getDailyPurchaseReport(date);
		
		 Iterator<ItemPurchaseDetail> ipdIter =  dailyPurchasesReport.getItemPurchaseDetails().iterator();
		 while(ipdIter.hasNext()) {
			 ItemPurchaseDetail ipd = ipdIter.next();
			 miListTMP.add(ipd.getMenuItem());
		 }
		 
		 List<MenuItem> uniqueMiList = new ArrayList<MenuItem>(new HashSet<MenuItem>(miListTMP));
		 
		 Iterator<MenuItem> uniqueMiListIter = uniqueMiList.iterator();
		
		 
		 while(uniqueMiListIter.hasNext()) {
			 MenuItem menuIten =  uniqueMiListIter.next();
			 sales = 0;
			 totalPrice = 0;

			 for(ItemPurchaseDetail ipd: dailyPurchasesReport.getItemPurchaseDetails()) {
				 if(ipd.getMenuItem().getId() == menuIten.getId()) {					 
					 sales+=ipd.getItems();
					 totalPrice+=ipd.getPrice()*ipd.getItems();
				 }
			 }
			 
			// dailyPurchasesReport.getItemPurchaseDetailOptions().get(0).get getItemOption().getPrice()

			 ItemsSoldReport itemsSoldReport = new ItemsSoldReport();
			 MenuItem parentItem = getParentItem(menuItemList, menuIten);
			 itemsSoldReport.setParentCaption(parentItem.getCaption());
			 itemsSoldReport.setCaption(menuIten.getCaption());
			 itemsSoldReport.setSales(sales);
			 itemsSoldReport.setTotalPrice(totalPrice);
			 itemsSoldReportList.add(itemsSoldReport);
			 
			 for(ItemPurchaseDetail ipd: dailyPurchasesReport.getItemPurchaseDetails()) {
				 if(ipd.getMenuItem().getId() == menuIten.getId()) {					 
					 
					 List<ItemPurchaseDetailOption> o_ipdo = dailyPurchasesReport.getItemPurchaseDetailOptions().stream()
					 .filter(p->p.getItemPurchaseDetail().getId() == ipd.getId())
					 .collect(Collectors.toList());
			
					 for (ItemPurchaseDetailOption ipdo: o_ipdo) {
						      if(ipdo.isSelected() && ipdo.getMenuItemOption().getItemOption().getPrice() > 0) {
						    	  ItemsSoldReport itemsSoldReportOption = new ItemsSoldReport();
						    	  itemsSoldReportOption.setParentCaption(parentItem.getCaption()+"--Option");
						    	  itemsSoldReportOption.setCaption(ipdo.getMenuItemOption().getItemOption().getDescription());
						    	  itemsSoldReportOption.setSales(1f);
						    	  itemsSoldReportOption.setTotalPrice(ipdo.getMenuItemOption().getItemOption().getPrice());
						    	  itemsSoldReportList.add(itemsSoldReportOption);
						    	  
						      }
						 
						 
					 }
				 }
			 }
			 
			 
		 }
		return itemsSoldReportList;
	}
	
	
	MenuItem getParentItem(List<MenuItem> menuItemList, MenuItem menuItem) {
		
		MenuItem parent = null;
		
		parent = getMenuItemParent( menuItemList, menuItem);
		
		return parent;
		
	}
	
	MenuItem getMenuItemParent(List<MenuItem> menuItemList, MenuItem menuItem) {
		
		MenuItem parent = null;
		
		List<MenuItem> menuItemListTmp = new ArrayList<MenuItem>(); // = menuItemList.stream().filter(mi -> mi.getId() == menuItem.getLinkedId()).collect(Collectors.toList());
		for(MenuItem mi: menuItemList) {
			if(mi.getId() ==  menuItem.getLinkedId()) {
				menuItemListTmp.add(mi);
			}
		}
		if(menuItemListTmp.size() > 0) {
			this.previousMenuItem = menuItem;
			parent = getMenuItemParent(menuItemList, menuItemListTmp.get(0));
			
		} else {
			if(this.application.getId() == 4) {
			//	this.previousMenuItem.setCaption(menuItem.getCaption() + "-" + this.previousMenuItem.getCaption());
			    menuItem = this.previousMenuItem; //2023-11-29. Just before Primary-School and Secondary Schhol
			}
			parent = menuItem;
		}
		return parent;
	}


	@Override
	public List<ItemsSoldReport> getAccountantReportNew(String date) {
		float sales;
		double totalPrice;
		
	
		
		String qlString = "select mi from MenuItem mi ";
		List<MenuItem> menuItemList = entityManager
				.createQuery(qlString,MenuItem.class).getResultList();
		
		List<ItemsSoldReport> itemsSoldReportList = new ArrayList<ItemsSoldReport>();
	
		List<MenuItem> miListTMP = new ArrayList<MenuItem>();
		DailyPurchasesReport dailyPurchasesReport = this.getDailyPurchaseReport(date);
		
		 Iterator<ItemPurchaseDetail> ipdIter =  dailyPurchasesReport.getItemPurchaseDetails().iterator();
		 while(ipdIter.hasNext()) {
			 ItemPurchaseDetail ipd = ipdIter.next();
			 miListTMP.add(ipd.getMenuItem());
		 }
		 
		 List<MenuItem> uniqueMiList = new ArrayList<MenuItem>(new HashSet<MenuItem>(miListTMP));
		 
		 Iterator<MenuItem> uniqueMiListIter = uniqueMiList.iterator();
		
		 
		 while(uniqueMiListIter.hasNext()) {
			 MenuItem menuIten =  uniqueMiListIter.next();
			 sales = 0;
			 totalPrice = 0;

			 for(ItemPurchaseDetail ipd: dailyPurchasesReport.getItemPurchaseDetails()) {
				 if(ipd.getMenuItem().getId() == menuIten.getId()) {					 
					 sales+=ipd.getItems();
					 totalPrice+=ipd.getPrice()*ipd.getItems();
				 }
			 }
			 
			// dailyPurchasesReport.getItemPurchaseDetailOptions().get(0).get getItemOption().getPrice()

			 ItemsSoldReport itemsSoldReport = new ItemsSoldReport();
			 MenuItem parentItem = getParentItem(menuItemList, menuIten);
			 itemsSoldReport.setParentCaption(parentItem.getCaption());
			 itemsSoldReport.setCaption(menuIten.getCaption());
			 itemsSoldReport.setSales(sales);
			 itemsSoldReport.setTotalPrice(totalPrice);
			 itemsSoldReportList.add(itemsSoldReport);
			 
			 for(ItemPurchaseDetail ipd: dailyPurchasesReport.getItemPurchaseDetails()) {
				 if(ipd.getMenuItem().getId() == menuIten.getId()) {					 
					 
					 List<ItemPurchaseDetailOption> o_ipdo = dailyPurchasesReport.getItemPurchaseDetailOptions().stream()
					 .filter(p->p.getItemPurchaseDetail().getId() == ipd.getId())
					 .collect(Collectors.toList());
			
					 for (ItemPurchaseDetailOption ipdo: o_ipdo) {
						      if(ipdo.isSelected() && ipdo.getMenuItemOption().getItemOption().getPrice() > 0) {
						    	  ItemsSoldReport itemsSoldReportOption = new ItemsSoldReport();
						    	  itemsSoldReportOption.setParentCaption(parentItem.getCaption()+"--Option");
						    	  itemsSoldReportOption.setCaption(ipdo.getMenuItemOption().getItemOption().getDescription());
						    	  itemsSoldReportOption.setSales(1f);
						    	  itemsSoldReportOption.setTotalPrice(ipdo.getMenuItemOption().getItemOption().getPrice());
						    	  itemsSoldReportList.add(itemsSoldReportOption);
						    	  
						      }
						 
						 
					 }
				 }
			 }
			 
			 
		 }
		return itemsSoldReportList;
	}



}
