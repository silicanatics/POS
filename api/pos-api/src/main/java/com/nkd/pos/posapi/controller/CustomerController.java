package com.nkd.pos.posapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nkd.pos.posapi.entity.Customer;
import com.nkd.pos.posapi.entity.CustomerDiscount;
import com.nkd.pos.posapi.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/getCustomers",produces = MediaType.APPLICATION_JSON_VALUE)
	List<Customer> getCustomers(){
		log.info("getCustomers");
		return customerService.getAll();
	}
	
	
	@RequestMapping(value = "/getCustomerDiscounts",produces = MediaType.APPLICATION_JSON_VALUE)
	List<CustomerDiscount> getCustomerDiscounts(){
		log.info("getCustomerDiscounts");
		return customerService.getAllCustomerDiscounts();
	}
}
