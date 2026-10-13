package com.nkd.pos.posapi.entity;

import java.io.Serializable;

public class StockCalculationPayload implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int purchaseItemId;
	private int itemPurchaseDetaild;
	private int menuItemId;
	private float items;
	public StockCalculationPayload() {
		super();
	}

	public StockCalculationPayload(int purchaseItemId, int itemPurchaseDetaild, int menuItemId, float items) {
		super();
		this.purchaseItemId = purchaseItemId;
		this.itemPurchaseDetaild = itemPurchaseDetaild;
		this.menuItemId = menuItemId;
		this.items = items;
	}

	public int getPurchaseItemId() {
		return purchaseItemId;
	}
	public void setPurchaseItemId(int purchaseItemId) {
		this.purchaseItemId = purchaseItemId;
	}
	public int getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}
	
	public int getItemPurchaseDetaild() {
		return itemPurchaseDetaild;
	}

	public void setItemPurchaseDetaild(int itemPurchaseDetaild) {
		this.itemPurchaseDetaild = itemPurchaseDetaild;
	}

	public float getItems() {
		return items;
	}

	public void setItems(float items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "StockCalculationPayload [purchaseItemId=" + purchaseItemId + ", itemPurchaseDetaild="
				+ itemPurchaseDetaild + ", menuItemId=" + menuItemId + ", items=" + items + "]";
	}


	
	
}
