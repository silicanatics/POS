package com.nkd.pos.posapi.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Applications")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	private String name;
	private byte menuTileCols;
	private String menuTileRowHeight;
	private LocalDate licenseExpireryDate;
	private Long  daysToExpirery;
	@Column(columnDefinition = "boolean default false")
	private boolean licenseExpired;
	public Application() {
		super();
	}



	public Application(int id, String name, byte menuTileCols, String menuTileRowHeight) {
		super();
		this.id = id;
		this.name = name;
		this.menuTileCols = menuTileCols;
		this.menuTileRowHeight = menuTileRowHeight;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getMenuTileCols() {
		return menuTileCols;
	}

	public void setMenuTileCols(byte menuTileCols) {
		this.menuTileCols = menuTileCols;
	}

	
	public String getMenuTileRowHeight() {
		return menuTileRowHeight;
	}



	public void setMenuTileRowHeight(String menuTileRowHeight) {
		this.menuTileRowHeight = menuTileRowHeight;
	}


	public LocalDate getLicenseExpireryDate() {
		return licenseExpireryDate;
	}

	public void setLicenseExpireryDate(LocalDate licenseExpireryDate) {
		this.licenseExpireryDate = licenseExpireryDate;
	}

	public boolean isLicenseExpired() {
		return licenseExpired;
	}


	public void setLicenseExpired(boolean licenseExpired) {
		this.licenseExpired = licenseExpired;
	}

	
	public Long getDaysToExpirery() {
		return daysToExpirery;
	}


	public void setDaysToExpirery(Long daysToExpirery) {
		this.daysToExpirery = daysToExpirery;
	}


	@Override
	public String toString() {
		return "Application [id=" + id + ", name=" + name + ", menuTileCols=" + menuTileCols + "]";
	}

	
}
