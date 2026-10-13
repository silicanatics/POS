package com.nkd.pos.posapi.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.nkd.pos.posapi.entity.DeliveryCharge;
import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.PaymentType;
import com.nkd.pos.posapi.entity.PurchaseDetail;
import com.nkd.pos.posapi.entity.PurchaseType;

public interface ItemPurchaseService {
	public ItemPurchase save(ItemPurchase itemPurchase);
	public List<PurchaseDetail> findPurchases(int stateId);
	public List<PurchaseDetail> findPurchasesByType(int purchaseTypeId, String date);
	public List<PurchaseDetail> findPurchasesByDate(String date);
	public void delete(ItemPurchase itemPurchase);
	public List<DeliveryCharge> getDeliveryChargeList();
	public List<PaymentType> getPaymentTypeList();
	public List<PurchaseType> getPurchaseTypeList();
	
	
}
