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
@Table(name="ItemPurchaseDetails")
public class ItemPurchaseDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name = "itemPurchaseId")
	private ItemPurchase itemPurchase;
	@ManyToOne
	@JoinColumn(name = "menuItemId")
	private MenuItem menuItem;
	private float price;
	private float items;
	public ItemPurchaseDetail() {
		super();
	}


	public ItemPurchaseDetail(int id, ItemPurchase itemPurchase, MenuItem menuItem, float price, int items) {
		super();
		this.id = id;
		this.itemPurchase = itemPurchase;
		this.menuItem = menuItem;
		this.price = price;
		this.items = items;
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
	
	public ItemPurchase getItemPurchase() {
		return itemPurchase;
	}

	public void setItemPurchase(ItemPurchase itemPurchase) {
		this.itemPurchase = itemPurchase;
	}

	public float getItems() {
		return items;
	}

	public void setItems(float items) {
		this.items = items;
	}
	
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "ItemPurchaseDetail [id=" + id + ", itemPurchase=" + itemPurchase + ", menuItem=" + menuItem + ", price="
				+ price + ", items=" + items + "]";
	}


	
}
