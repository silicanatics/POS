package com.nkd.pos.posapi.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.nkd.pos.posapi.entity.PurchasePayload;
import com.nkd.pos.posapi.entityReports.ChartReport;

public interface ReportService {
	public byte[] getInvoiceImage(PurchasePayload purchasePayload)  throws IOException, java.io.IOException;
	public byte[] getItemsSoldReport(String date);
	public void printReciept(PurchasePayload purchasePayload);
	public void printTest(byte[] pdfbyte);
	public List<ChartReport> getCharts(int menuId, String strDate);
}
