package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.StockStorage;

public interface StockStorageRepository extends JpaRepository<StockStorage, Integer> {

}
