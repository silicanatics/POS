package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.PurchasePayload;

public interface PurchaseService {
public PurchasePayload completePurchase(PurchasePayload purchasePayload);
public PurchasePayload savePurchase(PurchasePayload purchasePayload);
}
