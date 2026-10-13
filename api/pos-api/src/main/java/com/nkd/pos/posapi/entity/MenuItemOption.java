package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "MenuItemOptions")
public class MenuItemOption implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	private boolean defaultOption;
	@ManyToOne
	@JoinColumn(name = "itemOptionId")
	private ItemOption itemOption;
	@ManyToOne
	@JoinColumn(name = "menuItemId")
	private MenuItem menuItem;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMM/dd HH:mm:ss:SSS")
	private java.util.Date startDate;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMM/dd HH:mm:ss:SSS")
	private java.util.Date endDate;

	public MenuItemOption() {
		
	}

	public MenuItemOption(int id, boolean defaultOption, ItemOption itemOption, MenuItem menuItem, Date startDate,
			Date endDate) {
		super();
		this.id = id;
		this.defaultOption = defaultOption;
		this.itemOption = itemOption;
		this.menuItem = menuItem;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemOption getItemOption() {
		return itemOption;
	}

	public void setItemOption(ItemOption itemOption) {
		this.itemOption = itemOption;
	}

	public boolean isDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(boolean defaultOption) {
		this.defaultOption = defaultOption;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
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


	@Override
	public String toString() {
		return "MenuItemOption [id=" + id + ", defaultOption=" + defaultOption + ", itemOption=" + itemOption
				+ ", menuItem=" + menuItem + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}



	public void setId(Object object) {
		// TODO Auto-generated method stub
		
	}

}
