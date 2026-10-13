package com.nkd.pos.posapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="MachinePrinter")
public class MachinePrinter  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	private String ipAddress;
	private String machineName;
	@OneToOne
	@JoinColumn(name = "paymentTypeId")
	private PaymentType paymentType;
	private String printerName;
	private String kitchenPrinterName;
	private int clientFontSize;
	private int kitchenFontSize;
	public MachinePrinter() {
		super();
	}



	public MachinePrinter(int id, String ipAddress, String machineName, PaymentType paymentType, String printerName,
			String kitchenPrinterName, int clientFontSize, int kitchenFontSize) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.machineName = machineName;
		this.paymentType = paymentType;
		this.printerName = printerName;
		this.kitchenPrinterName = kitchenPrinterName;
		this.clientFontSize = clientFontSize;
		this.kitchenFontSize = kitchenFontSize;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	
	public String getKitchenPrinterName() {
		return kitchenPrinterName;
	}

	public void setKitchenPrinterName(String kitchenPrinterName) {
		this.kitchenPrinterName = kitchenPrinterName;
	}
	
	

	public int getClientFontSize() {
		return clientFontSize;
	}



	public void setClientFontSize(int clientFontSize) {
		this.clientFontSize = clientFontSize;
	}



	public int getKitchenFontSize() {
		return kitchenFontSize;
	}



	public void setKitchenFontSize(int kitchenFontSize) {
		this.kitchenFontSize = kitchenFontSize;
	}



	@Override
	public String toString() {
		return "MachinePrinter [id=" + id + ", ipAddress=" + ipAddress + ", machineName=" + machineName
				+ ", paymentType=" + paymentType + ", printerName=" + printerName + ", kitchenPrinterName="
				+ kitchenPrinterName + ", clientFontSize=" + clientFontSize + ", kitchenFontSize=" + kitchenFontSize
				+ "]";
	}






	
	
}
