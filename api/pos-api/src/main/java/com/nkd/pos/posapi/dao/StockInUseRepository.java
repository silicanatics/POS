package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.StockInUse;

public interface StockInUseRepository extends JpaRepository<StockInUse, Integer>{

}
