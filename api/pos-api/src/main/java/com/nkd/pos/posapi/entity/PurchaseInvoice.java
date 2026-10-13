package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="PurchaseInvoices")
public class PurchaseInvoice implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoiceNumber", unique = true, nullable = false)
	private Long invoiceNumber;
	private int purchaseNumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private java.sql.Date invoiceDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss:SSS")
	private java.util.Date invoiceDateTime;
	public PurchaseInvoice() {
		super();
	}


	public PurchaseInvoice(Long invoiceNumber, int purchaseNumber, Date invoiceDate, java.util.Date invoiceDateTime) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.purchaseNumber = purchaseNumber;
		this.invoiceDate = invoiceDate;
		this.invoiceDateTime = invoiceDateTime;
	}


	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public int getPurchaseNumber() {
		return purchaseNumber;
	}
	public void setPurchaseNumber(int purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}


	public java.sql.Date getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(java.sql.Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	public java.util.Date getInvoiceDateTime() {
		return invoiceDateTime;
	}


	public void setInvoiceDateTime(java.util.Date invoiceDateTime) {
		this.invoiceDateTime = invoiceDateTime;
	}


	@Override
	public String toString() {
		return "PurchaseInvoice [invoiceNumber=" + invoiceNumber + ", purchaseNumber=" + purchaseNumber
				+ ", invoiceDate=" + invoiceDate + ", invoiceDateTime=" + invoiceDateTime + "]";
	}
	
	
}
