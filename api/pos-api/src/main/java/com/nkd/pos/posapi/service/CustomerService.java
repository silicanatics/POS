package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.Customer;
import com.nkd.pos.posapi.entity.CustomerDiscount;

public interface CustomerService {
public Customer getById(int id);
public List<Customer> getAll();
public List<CustomerDiscount> getAllCustomerDiscounts();
public Customer save(Customer customer);
public Customer updateCustomerAddressAndContact(Customer customer);
}
