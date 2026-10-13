package com.nkd.pos.posapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="purchases_discounts")
public class PurchasesDiscount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	private float discountPercent;
	private float threasholdAmount;
	public PurchasesDiscount() {
		super();
	}
	public PurchasesDiscount(int id, float discountPercent, float threasholdAmount) {
		super();
		this.id = id;
		this.discountPercent = discountPercent;
		this.threasholdAmount = threasholdAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	public float getThreasholdAmount() {
		return threasholdAmount;
	}
	public void setThreasholdAmount(float threasholdAmount) {
		this.threasholdAmount = threasholdAmount;
	}
	@Override
	public String toString() {
		return "PurchasesDiscount [id=" + id + ", discountPercent=" + discountPercent + ", threasholdAmount="
				+ threasholdAmount + "]";
	}
	
	
}
