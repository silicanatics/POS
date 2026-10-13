package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

}
