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
@Table(name = "ItemPurchaseDetailOptions")
public class ItemPurchaseDetailOption implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
    @Transient
	private ItemOption itemOption;
    @ManyToOne
    @JoinColumn(name = "itemPurchaseDetailId")
    private ItemPurchaseDetail itemPurchaseDetail;
    @ManyToOne
    @JoinColumn(name = "menuItemOptionId")
    private MenuItemOption menuItemOption;
    private boolean selected;
	public ItemPurchaseDetailOption() {
		super();
	}

	public ItemPurchaseDetailOption(int id, ItemOption itemOption, ItemPurchaseDetail itemPurchaseDetail,
			MenuItemOption menuItemOption, boolean selected) {
		super();
		this.id = id;
		this.itemOption = itemOption;
		this.itemPurchaseDetail = itemPurchaseDetail;
		this.menuItemOption = menuItemOption;
		this.selected = selected;
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
	
	public ItemPurchaseDetail getItemPurchaseDetail() {
		return itemPurchaseDetail;
	}

	public void setItemPurchaseDetail(ItemPurchaseDetail itemPurchaseDetail) {
		this.itemPurchaseDetail = itemPurchaseDetail;
	}

	
	public MenuItemOption getMenuItemOption() {
		return menuItemOption;
	}

	public void setMenuItemOption(MenuItemOption menuItemOption) {
		this.menuItemOption = menuItemOption;
	}

	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "ItemPurchaseDetailOption [id=" + id + ", itemOption=" + itemOption + ", itemPurchaseDetail="
				+ itemPurchaseDetail + ", menuItemOption=" + menuItemOption + ", selected=" + selected + "]";
	}


}
