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
@Table(name="stock_items")
public class StockItem  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "menuItemId")
	private MenuItem  menuItem;	
	
	private String caption;
	
	private int totalQuantity;
	
	@ManyToOne
	@JoinColumn(name = "quantityTypeId")
	private QuantityType quantityType;

	public StockItem() {
		super();
	}


	public StockItem(int id, MenuItem menuItem, String caption, int totalQuantity, QuantityType quantityType) {
		super();
		this.id = id;
		this.menuItem = menuItem;
		this.caption = caption;
		this.totalQuantity = totalQuantity;
		this.quantityType = quantityType;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public QuantityType getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
	}
	
	

	public String getCaption() {
		return caption;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}


	@Override
	public String toString() {
		return "StockItem [id=" + id + ", menuItem=" + menuItem + ", caption=" + caption + ", totalQuantity="
				+ totalQuantity + ", quantityType=" + quantityType + "]";
	}


	
	
	
}
