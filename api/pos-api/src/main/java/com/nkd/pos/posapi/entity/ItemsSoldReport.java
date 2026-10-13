package com.nkd.pos.posapi.entity;

import java.io.Serializable;

public class ItemsSoldReport  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String parentCaption;
	String caption;
	float sales;
	double totalPrice;
	
	public ItemsSoldReport() {
		super();
	}	
	

	public ItemsSoldReport(String parentCaption, String caption, float sales, double totalPrice) {
		super();
		this.parentCaption = parentCaption;
		this.caption = caption;
		this.sales = sales;
		this.totalPrice = totalPrice;
	}
	
	public String getParentCaption() {
		return parentCaption;
	}

	public void setParentCaption(String parentCaption) {
		this.parentCaption = parentCaption;
	}


	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public float getSales() {
		return sales;
	}
	public void setSales(float sales) {
		this.sales = sales;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "ItemsSoldReport [parentCaption=" + parentCaption + ", caption=" + caption + ", sales=" + sales
				+ ", totalPrice=" + totalPrice + "]";
	}

	
}
