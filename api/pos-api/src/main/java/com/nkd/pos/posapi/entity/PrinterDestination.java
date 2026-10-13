package com.nkd.pos.posapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PrinterDestinations")
public class PrinterDestination  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	private String destination;
	private String defaultPrinter;
	
	
	public PrinterDestination() {
		super();
	}


	public PrinterDestination(int id, String destination, String defaultPrinter) {
		super();
		this.id = id;
		this.destination = destination;
		this.defaultPrinter = defaultPrinter;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	public String getDefaultPrinter() {
		return defaultPrinter;
	}


	public void setDefaultPrinter(String defaultPrinter) {
		this.defaultPrinter = defaultPrinter;
	}


	@Override
	public String toString() {
		return "PrinterDestination [id=" + id + ", destination=" + destination + ", defaultPrinter=" + defaultPrinter
				+ "]";
	}

	
}
