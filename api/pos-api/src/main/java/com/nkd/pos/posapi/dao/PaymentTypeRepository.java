package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {

}
