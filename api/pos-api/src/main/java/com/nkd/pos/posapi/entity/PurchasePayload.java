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
import javax.persistence.Table;


public class PurchasePayload implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Usr user;
	private Customer customer;
	private PurchaseType purchaseType;
	private String clientIP;
	private ItemPurchase itemPurchase;
	private List<SelectedItemPurchaseDetail> selectedItemPurchaseDetails;
	public PurchasePayload() {
		super();
	}

	public PurchasePayload(int id, Usr user, Customer customer, PurchaseType purchaseType, String clientIP,
			ItemPurchase itemPurchase, List<SelectedItemPurchaseDetail> selectedItemPurchaseDetails) {
		super();
		this.id = id;
		this.user = user;
		this.customer = customer;
		this.purchaseType = purchaseType;
		this.clientIP = clientIP;
		this.itemPurchase = itemPurchase;
		this.selectedItemPurchaseDetails = selectedItemPurchaseDetails;
	}

	public ItemPurchase getItemPurchase() {
		return itemPurchase;
	}
	public void setItemPurchase(ItemPurchase itemPurchase) {
		this.itemPurchase = itemPurchase;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<SelectedItemPurchaseDetail> getSelectedItemPurchaseDetails() {
		return selectedItemPurchaseDetails;
	}

	public void setSelectedItemPurchaseDetails(List<SelectedItemPurchaseDetail> selectedItemPurchaseDetails) {
		this.selectedItemPurchaseDetails = selectedItemPurchaseDetails;
	}

	public Usr getUser() {
		return user;
	}

	public void setUser(Usr user) {
		this.user = user;
	}
	
	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Override
	public String toString() {
		return "PurchasePayload [id=" + id + ", user=" + user + ", clientIP=" + clientIP + ", itemPurchase="
				+ itemPurchase + ", selectedItemPurchaseDetails=" + selectedItemPurchaseDetails + "]";
	}


}
