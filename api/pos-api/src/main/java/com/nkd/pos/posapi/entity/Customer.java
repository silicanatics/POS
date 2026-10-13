package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Customers")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@OneToOne
	@JoinColumn(name="entityId")
	private DBEntity dbEntity;
	@Column(nullable = true)
	private String customerId;
	@Transient
	private List<EntityAddress> customerAddresses;
	@Transient
	private List<EntityContact> customerContacts;
	public Customer() {
		super();
	}
	public Customer(int id, DBEntity dbEntity, String customerId) {
		super();
		this.id = id;
		this.dbEntity = dbEntity;
		this.customerId = customerId;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<EntityAddress> getCustomerAddresses() {
		return customerAddresses;
	}
	public void setCustomerAddresses(List<EntityAddress> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}
	public List<EntityContact> getCustomerContacts() {
		return customerContacts;
	}
	public void setCustomerContacts(List<EntityContact> customerContacts) {
		this.customerContacts = customerContacts;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", dbEntity=" + dbEntity + ", customerId=" + customerId + ", customerAddresses="
				+ customerAddresses + ", customerContacts=" + customerContacts + "]";
	}

}
