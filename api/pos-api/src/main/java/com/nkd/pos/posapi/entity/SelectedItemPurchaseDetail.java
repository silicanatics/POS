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

public class SelectedItemPurchaseDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private int id;
	private float price;
	private MenuItem menuItem;
	private List<MenuItemOption> menuItemOptions;
	public SelectedItemPurchaseDetail() {
		super();
	}


	public SelectedItemPurchaseDetail(int id, float price, MenuItem menuItem, List<MenuItemOption> menuItemOptions) {
		super();
		this.id = id;
		this.price = price;
		this.menuItem = menuItem;
		this.menuItemOptions = menuItemOptions;
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
 
	public List<MenuItemOption> getMenuItemOptions() {
		return menuItemOptions;
	}

	public void setMenuItemOptions(List<MenuItemOption> menuItemOptions) {
		this.menuItemOptions = menuItemOptions;
	}
	
	

	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "SelectedItemPurchaseDetail [id=" + id + ", price=" + price + ", menuItem=" + menuItem
				+ ", menuItemOptions=" + menuItemOptions + "]";
	}



	
}
