package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.ItemPurchaseDetail;

public interface ItemPurchaseDetailRepository extends JpaRepository<ItemPurchaseDetail,Integer> { 

}
