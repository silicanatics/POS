package com.nkd.pos.posapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ClientPrinters")
public class ClientPrinter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	private String ipAddress;
	private String printerName;
	
	@ManyToOne
	@JoinColumn(name = "printer_dest_id")
	private PrinterDestination printerDestination;

	public ClientPrinter() {
		super();
	}


	public ClientPrinter(int id, String ipAddress, String printerName, PrinterDestination printerDestination) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.printerName = printerName;
		this.printerDestination = printerDestination;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

		
	public PrinterDestination getPrinterDestination() {
		return printerDestination;
	}


	public void setPrinterDestination(PrinterDestination printerDestination) {
		this.printerDestination = printerDestination;
	}


	@Override
	public String toString() {
		return "ClientPrinter [id=" + id + ", ipAddress=" + ipAddress + ", printerName=" + printerName
				+ ", printerDestination=" + printerDestination + "]";
	}

	
}
