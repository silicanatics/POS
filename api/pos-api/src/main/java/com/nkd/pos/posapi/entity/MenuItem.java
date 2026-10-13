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
import javax.persistence.Transient;

@Entity
@Table(name="MenuItems")
public class MenuItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	private int linkedId;
	private String caption;
	private double price;
	private double vat;
	private int menuLevel;
	@ManyToOne
	@JoinColumn(name = "nodeTypeId")
	private NodeType nodeType;
	@ManyToOne
	@JoinColumn(name = "ItemTypeId")
	private MenuItemType menuItemType;
	@ManyToOne
	@JoinColumn(name = "menuItemCategoryId")
	private MenuItemCategory menuItemCategory;
	
	@Transient
	private float items;
	private String barcode;
	private String icon;
	
	public MenuItem() {
		super();
	}




	public MenuItem(int id, int linkedId, String caption, double price, double vat, int menuLevel, NodeType nodeType,
			MenuItemType menuItemType, MenuItemCategory menuItemCategory, int items, String barcode, String icon) {
		super();
		this.id = id;
		this.linkedId = linkedId;
		this.caption = caption;
		this.price = price;
		this.vat = vat;
		this.menuLevel = menuLevel;
		this.nodeType = nodeType;
		this.menuItemType = menuItemType;
		this.menuItemCategory = menuItemCategory;
		this.items = items;
		this.barcode = barcode;
		this.icon = icon;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLinkedId() {
		return linkedId;
	}

	public void setLinkedId(int linkedId) {
		this.linkedId = linkedId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	

	public float getItems() {
		return items;
	}



	public void setItems(float items) {
		this.items = items;
	}
	
	

	public double getVat() {
		return vat;
	}


	public void setVat(double vat) {
		this.vat = vat;
	}
	
	public MenuItemType getMenuItemType() {
		return menuItemType;
	}


	public void setMenuItemType(MenuItemType menuItemType) {
		this.menuItemType = menuItemType;
	}

	

	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public MenuItemCategory getMenuItemCategory() {
		return menuItemCategory;
	}




	public void setMenuItemCategory(MenuItemCategory menuItemCategory) {
		this.menuItemCategory = menuItemCategory;
	}




	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", linkedId=" + linkedId + ", caption=" + caption + ", price=" + price + ", vat="
				+ vat + ", menuLevel=" + menuLevel + ", nodeType=" + nodeType + ", menuItemType=" + menuItemType
				+ ", items=" + items + ", barcode=" + barcode + "]";
	}

	
}
