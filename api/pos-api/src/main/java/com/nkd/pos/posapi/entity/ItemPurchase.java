package com.nkd.pos.posapi.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name="ItemPurchases")
public class ItemPurchase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@OneToOne
	@JoinColumn(name = "purchaseInvoiceId")
	private PurchaseInvoice purchaseInvoice;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss:SSS")
	private LocalDateTime purchaseDate;
	private float amount;
	private float amountTendered;
	private float change_;
	private float discount;
	private float deliveryCharge;
	@OneToOne
	@JoinColumn(name = "paymentTypeId")
	private PaymentType paymentType;
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "purchaseTypeId")
	private PurchaseType purchaseType;
	@ManyToOne
	@JoinColumn(name = "purchaseStateId")
	private PurchaseState purchaseState;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = true)
	private Usr user;
	
	public ItemPurchase() {
		super();
	}


	public ItemPurchase(int id, PurchaseInvoice purchaseInvoice, LocalDateTime purchaseDate, float amount,
			float amountTendered, float change_, float discount, float deliveryCharge, PaymentType paymentType,
			Customer customer, PurchaseType purchaseType, PurchaseState purchaseState, Usr user) {
		super();
		this.id = id;
		this.purchaseInvoice = purchaseInvoice;
		this.purchaseDate = purchaseDate;
		this.amount = amount;
		this.amountTendered = amountTendered;
		this.change_ = change_;
		this.discount = discount;
		this.deliveryCharge = deliveryCharge;
		this.paymentType = paymentType;
		this.customer = customer;
		this.purchaseType = purchaseType;
		this.purchaseState = purchaseState;
		this.user = user;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PurchaseInvoice getPurchaseInvoice() {
		return purchaseInvoice;
	}

	public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmountTendered() {
		return amountTendered;
	}

	public void setAmountTendered(float amountTendered) {
		this.amountTendered = amountTendered;
	}

	public float getChange_() {
		return change_;
	}

	public void setChange_(float change_) {
		this.change_ = change_;
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

	public PurchaseState getPurchaseState() {
		return purchaseState;
	}

	public void setPurchaseState(PurchaseState purchaseState) {
		this.purchaseState = purchaseState;
	}

	public Usr getUser() {
		return user;
	}

	public void setUser(Usr user) {
		this.user = user;
	}

	
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	
	public float getDiscount() {
		return discount;
	}


	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public float getDeliveryCharge() {
		return deliveryCharge;
	}


	public void setDeliveryCharge(float deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}


	@Override
	public String toString() {
		return "ItemPurchase [id=" + id + ", purchaseInvoice=" + purchaseInvoice + ", purchaseDate=" + purchaseDate
				+ ", amount=" + amount + ", amountTendered=" + amountTendered + ", change_=" + change_ + ", discount="
				+ discount + ", deliveryCharge=" + deliveryCharge + ", paymentType=" + paymentType + ", customer="
				+ customer + ", purchaseType=" + purchaseType + ", purchaseState=" + purchaseState + ", user=" + user
				+ "]";
	}



}
