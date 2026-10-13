package com.nkd.pos.posapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="UserPrinters")
public class UserPrinter  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private Usr user;
	
	@ManyToOne
	@JoinColumn(name = "printer_dest_id")
	private PrinterDestination printerDestination;
	
	private String printerName;
	
	

	public UserPrinter() {
		super();
	}

	public UserPrinter(int id, Usr user, PrinterDestination printerDestination, String printerName) {
		super();
		this.id = id;
		this.user = user;
		this.printerDestination = printerDestination;
		this.printerName = printerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usr getUser() {
		return user;
	}

	public void setUser(Usr user) {
		this.user = user;
	}

	public PrinterDestination getPrinterDestination() {
		return printerDestination;
	}

	public void setPrinterDestination(PrinterDestination printerDestination) {
		this.printerDestination = printerDestination;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	@Override
	public String toString() {
		return "UserPrinters [id=" + id + ", user=" + user + ", printerDestination=" + printerDestination
				+ ", printerName=" + printerName + "]";
	}  
	
		
}
