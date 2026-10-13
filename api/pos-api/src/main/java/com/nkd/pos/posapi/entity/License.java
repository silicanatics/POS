package com.nkd.pos.posapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "license")
public class License {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	private String license;
	
	private LocalDate expireryDate;
	private String dateUpdated;

	public License() {
		super();
	}


	public License(int id, String license) {
		super();
		this.id = id;
		this.license = license;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}



	@Override
	public String toString() {
		return "License [id=" + id + ", license=" + license + "]";
	}


}
