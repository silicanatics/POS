package com.nkd.pos.posapi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="ItemLayBuys")
public class ItemLayBuy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "itemPurchaseId")
	private ItemPurchase itemPurchase;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss:SSS")
	private LocalDateTime paymentDate;
	
	private float amount;
	private float outstandingAmount;
	public ItemLayBuy() {
		super();
	}
	public ItemLayBuy(int id, ItemPurchase itemPurchase, LocalDateTime paymentDate, float amount,
			float outstandingAmount) {
		super();
		this.id = id;
		this.itemPurchase = itemPurchase;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.outstandingAmount = outstandingAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ItemPurchase getItemPurchase() {
		return itemPurchase;
	}
	public void setItemPurchase(ItemPurchase itemPurchase) {
		this.itemPurchase = itemPurchase;
	}
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(float outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	@Override
	public String toString() {
		return "ItemLayBuy [id=" + id + ", itemPurchase=" + itemPurchase + ", paymentDate=" + paymentDate + ", amount="
				+ amount + ", outstandingAmount=" + outstandingAmount + "]";
	}
	
	
}
