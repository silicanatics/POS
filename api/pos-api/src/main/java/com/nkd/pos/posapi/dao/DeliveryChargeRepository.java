package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nkd.pos.posapi.entity.DeliveryCharge;

public interface DeliveryChargeRepository extends JpaRepository<DeliveryCharge, Integer>{

}
