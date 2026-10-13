package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.CustomerDiscount;

public interface CustomerDiscountRepository extends JpaRepository<CustomerDiscount, Integer> {

}
