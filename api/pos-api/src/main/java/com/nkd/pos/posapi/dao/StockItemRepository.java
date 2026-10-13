package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.nkd.pos.posapi.entity.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem,Integer> {

}
