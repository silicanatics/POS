package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.ItemPurchaseDetail;

public interface ItemPurchaseDetailService {
public ItemPurchaseDetail save(ItemPurchaseDetail itemPurchaseDetail);
public List<ItemPurchaseDetail> findAll();
public void removeAll(List<ItemPurchaseDetail> itemPurchaseDetailList);
}
