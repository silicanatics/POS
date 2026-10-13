package com.nkd.pos.posapi.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="CustomerDiscounts")
public class CustomerDiscount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name="itemPurchaseId")
	private ItemPurchase itemPurchase;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss:SSS")
	private java.util.Date startDate;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss:SSS")
	private java.util.Date endDate;
	private float accumulatedAmount;
	private float threasholdAmount;
	private float discountPercent;
	private float discount;
	private int purchases;
	public CustomerDiscount() {
		super();
	}

	public CustomerDiscount(int id, Customer customer, ItemPurchase itemPurchase, Date startDate, Date endDate,
			float accumulatedAmount, float threasholdAmount, float discountPercent, float discount, int purchases) {
		super();
		this.id = id;
		this.customer = customer;
		this.itemPurchase = itemPurchase;
		this.startDate = startDate;
		this.endDate = endDate;
		this.accumulatedAmount = accumulatedAmount;
		this.threasholdAmount = threasholdAmount;
		this.discountPercent = discountPercent;
		this.discount = discount;
		this.purchases = purchases;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public float getAccumulatedAmount() {
		return accumulatedAmount;
	}
	public void setAccumulatedAmount(float accumulatedAmount) {
		this.accumulatedAmount = accumulatedAmount;
	}
	public float getThreasholdAmount() {
		return threasholdAmount;
	}
	public void setThreasholdAmount(float threasholdAmount) {
		this.threasholdAmount = threasholdAmount;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	public int getPurchases() {
		return purchases;
	}
	public void setPurchases(int purchases) {
		this.purchases = purchases;
	}
	
	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}
	

	public java.util.Date getStartDate() {
		return startDate;
	}



	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}



	public java.util.Date getEndDate() {
		return endDate;
	}



	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}



	public ItemPurchase getItemPurchase() {
		return itemPurchase;
	}

	public void setItemPurchase(ItemPurchase itemPurchase) {
		this.itemPurchase = itemPurchase;
	}

	@Override
	public String toString() {
		return "CustomerDiscount [id=" + id + ", customer=" + customer + ", itemPurchase=" + itemPurchase
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", accumulatedAmount=" + accumulatedAmount
				+ ", threasholdAmount=" + threasholdAmount + ", discountPercent=" + discountPercent + ", discount="
				+ discount + ", purchases=" + purchases + "]";
	}

	
}
