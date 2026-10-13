package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.ItemPurchaseDetailOption;

public interface ItemPurchaseDetailOptionService {
 
	public ItemPurchaseDetailOption save(ItemPurchaseDetailOption itemPurchaseDetailOption);
	public void removeAll(List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList);
}
