package com.nkd.pos.posapi.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.PurchaseState;

public interface ItemPurchaseRepository extends JpaRepository<ItemPurchase,Long> {

	
	public List<ItemPurchase> getByPurchaseState(PurchaseState purchaseState);
}
