package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.util.List;

public class PurchaseDetail  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
private int id;
private ItemPurchase itemPurchase;
private List<ItemPurchaseDetail> itemPurchaseDetailList;
private List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList;
public PurchaseDetail() {
	super();
}

public PurchaseDetail(int id, ItemPurchase itemPurchase, List<ItemPurchaseDetail> itemPurchaseDetailList,
		List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList) {
	super();
	this.id = id;
	this.itemPurchase = itemPurchase;
	this.itemPurchaseDetailList = itemPurchaseDetailList;
	this.itemPurchaseDetailOptionList = itemPurchaseDetailOptionList;
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

public List<ItemPurchaseDetail> getItemPurchaseDetailList() {
	return itemPurchaseDetailList;
}

public void setItemPurchaseDetailList(List<ItemPurchaseDetail> itemPurchaseDetailList) {
	this.itemPurchaseDetailList = itemPurchaseDetailList;
}

public List<ItemPurchaseDetailOption> getItemPurchaseDetailOptionList() {
	return itemPurchaseDetailOptionList;
}

public void setItemPurchaseDetailOptionList(List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList) {
	this.itemPurchaseDetailOptionList = itemPurchaseDetailOptionList;
}

@Override
public String toString() {
	return "PurchaseDetail [id=" + id + ", itemPurchase=" + itemPurchase + ", itemPurchaseDetailList="
			+ itemPurchaseDetailList + ", itemPurchaseDetailOptionList=" + itemPurchaseDetailOptionList + "]";
}

}
