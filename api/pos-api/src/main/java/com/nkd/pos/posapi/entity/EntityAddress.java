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
@Table(name="EntityAddresses")
public class EntityAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name="entityId")
	private DBEntity dbEntity;
	private String addressText;
	@Column(nullable = true)
	private String line1;
	@Column(nullable = true)
	private String line2;
	@Column(nullable = true)
	private String line3;
	@Column(nullable = true)
	private String line4;
	@Column(nullable = true)
	private int postalCode;
	public EntityAddress() {
		super();
	}
	public EntityAddress(int id, DBEntity dbEntity, String addressText, String line1, String line2, String line3,
			String line4, int postalCode) {
		super();
		this.id = id;
		this.dbEntity = dbEntity;
		this.addressText = addressText;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		this.line4 = line4;
		this.postalCode = postalCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public DBEntity getDbEntity() {
		return dbEntity;
	}
	public void setDbEntity(DBEntity dbEntity) {
		this.dbEntity = dbEntity;
	}
	public String getAddressText() {
		return addressText;
	}
	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public String getLine4() {
		return line4;
	}
	public void setLine4(String line4) {
		this.line4 = line4;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	@Override
	public String toString() {
		return "EntityAddress [id=" + id + ", dbEntity=" + dbEntity + ", addressText=" + addressText + ", line1="
				+ line1 + ", line2=" + line2 + ", line3=" + line3 + ", line4=" + line4 + ", postalCode=" + postalCode
				+ "]";
	}
		
}
