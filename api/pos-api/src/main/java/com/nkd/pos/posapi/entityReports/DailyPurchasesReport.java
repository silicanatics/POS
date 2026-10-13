package com.nkd.pos.posapi.entityReports;

import java.time.LocalDate;
import java.util.List;

import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.ItemPurchaseDetailOption;
import com.nkd.pos.posapi.entity.PurchaseInvoice;

public class DailyPurchasesReport {

	private LocalDate reportDate;
	private List<PurchaseInvoice> purchaseInvoices;
	private List<ItemPurchase> itemPurchases;
	private List<ItemPurchaseDetail> itemPurchaseDetails;
	private List<ItemPurchaseDetailOption> itemPurchaseDetailOptions;
	
	
	public DailyPurchasesReport() {
		super();
	}

	public DailyPurchasesReport(LocalDate reportDate, List<PurchaseInvoice> purchaseInvoices,
			List<ItemPurchase> itemPurchases, List<ItemPurchaseDetail> itemPurchaseDetails,
			List<ItemPurchaseDetailOption> itemPurchaseDetailOptions) {
		super();
		this.reportDate = reportDate;
		this.purchaseInvoices = purchaseInvoices;
		this.itemPurchases = itemPurchases;
		this.itemPurchaseDetails = itemPurchaseDetails;
		this.itemPurchaseDetailOptions = itemPurchaseDetailOptions;
	}

	public LocalDate getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}

	public List<PurchaseInvoice> getPurchaseInvoices() {
		return purchaseInvoices;
	}

	public void setPurchaseInvoices(List<PurchaseInvoice> purchaseInvoices) {
		this.purchaseInvoices = purchaseInvoices;
	}

	public List<ItemPurchase> getItemPurchases() {
		return itemPurchases;
	}

	public void setItemPurchases(List<ItemPurchase> itemPurchases) {
		this.itemPurchases = itemPurchases;
	}

	public List<ItemPurchaseDetail> getItemPurchaseDetails() {
		return itemPurchaseDetails;
	}

	public void setItemPurchaseDetails(List<ItemPurchaseDetail> itemPurchaseDetails) {
		this.itemPurchaseDetails = itemPurchaseDetails;
	}
	
	public List<ItemPurchaseDetailOption> getItemPurchaseDetailOptions() {
		return itemPurchaseDetailOptions;
	}

	public void setItemPurchaseDetailOptions(List<ItemPurchaseDetailOption> itemPurchaseDetailOptions) {
		this.itemPurchaseDetailOptions = itemPurchaseDetailOptions;
	}

	@Override
	public String toString() {
		return "DailyPurchaseReport [reportDate=" + reportDate + ", purchaseInvoices=" + purchaseInvoices
				+ ", itemPurchases=" + itemPurchases + ", itemPurchaseDetails=" + itemPurchaseDetails
				+ ", itemPurchaseDetailOptions=" + itemPurchaseDetailOptions + "]";
	}

	
}
