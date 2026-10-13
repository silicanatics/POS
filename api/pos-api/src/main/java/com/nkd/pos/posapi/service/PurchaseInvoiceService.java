package com.nkd.pos.posapi.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.nkd.pos.posapi.dao.PurchaseInvoiceRepository;
import com.nkd.pos.posapi.entity.PurchaseInvoice;

public interface PurchaseInvoiceService {

	public PurchaseInvoice savePurchaseInvoice(PurchaseInvoice purchaseInvoice);

	public PurchaseInvoice getNextPurchaseInvoice();

	public PurchaseInvoice getNextPurchaseInvoiceStatic();

	@SuppressWarnings("finally")
	public static PurchaseInvoice staticGetNextPurchaseInvoice(String userName, String password, String conUrl) {

		  LocalDate date = LocalDate.now();
		 
		 
		
		long invoiceNumber = 0;
		int purchaseNumber = 0;
		LocalDate maxInvoiceDate2 = LocalDate.now();
		Date maxInvoiceDate = Date.valueOf(maxInvoiceDate2);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
		LocalDate invoiceDate = LocalDate.now();
		LocalDateTime invoiceDateTime = LocalDateTime.now(ZoneId.of("Africa/Harare"));
		
		ReentrantLock lock = new ReentrantLock();
		PurchaseInvoice pi = new PurchaseInvoice();
		lock.lock();
		try {

			// Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(conUrl, userName, password);
			String query = "select * from purchase_invoices pi where pi.invoice_number = (select max(invoice_number) from purchase_invoices)";

			try (Statement stmt = conn.createStatement()) {
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					invoiceNumber = rs.getLong("invoice_number");
					purchaseNumber = rs.getInt("purchase_number");
					maxInvoiceDate = rs.getDate("invoice_date");
				}
			} catch (SQLException e) {

			}

			maxInvoiceDate2 = new java.sql.Date(maxInvoiceDate.getTime()).toLocalDate();
			if(!LocalDate.now().equals(maxInvoiceDate2)) {
				purchaseNumber = 0;
			}
			
			pi.setInvoiceNumber(invoiceNumber + 1);
			pi.setPurchaseNumber(purchaseNumber + 1);

			pi.setInvoiceDate(java.sql.Date.valueOf(invoiceDate));
			pi.setInvoiceDateTime(java.sql.Timestamp.valueOf(invoiceDateTime));
		}

		finally {
			lock.unlock();
			return pi;
		}
	}

	public Long getMaxInvoiceNumber();

	public PurchaseInvoice getMaxInvoice();
}
