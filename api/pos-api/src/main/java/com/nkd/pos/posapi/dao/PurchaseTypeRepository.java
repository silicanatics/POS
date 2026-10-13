package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.PurchaseType;

public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Integer> {

}
