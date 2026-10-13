package com.nkd.pos.posapi.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.controller.CustomerController;
import com.nkd.pos.posapi.dao.CustomerDiscountRepository;
import com.nkd.pos.posapi.dao.CustomerRepository;
import com.nkd.pos.posapi.dao.EntityAddressRepository;
import com.nkd.pos.posapi.dao.EntityContactRepository;
import com.nkd.pos.posapi.entity.Customer;
import com.nkd.pos.posapi.entity.CustomerDiscount;
import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.EntityContact;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	EntityManager entityManager;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	EntityAddressRepository entityAddressRepository;
	@Autowired
	EntityContactRepository entityContactRepository;
	@Autowired
	CustomerDiscountRepository customerDiscountRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Override
	public Customer getById(int id) {
		
		return customerRepository.getById(id);
	}
	
	private List<Customer> constructCustomerList(List<Customer> customerList){
		
//		List<EntityAddress> entitiyAddressList = entityAddressRepository.findAll();
//		List<EntityContact> entityContactList = entityContactRepository.findAll();

		for(Customer customer :customerList) {
						
			customer = updateCustomerAddressAndContact(customer);
//			List<EntityAddress> entityAddressListTmp = entitiyAddressList.stream().filter(ea -> ea.getDbEntity().getId() == customer.getDbEntity().getId()).collect(Collectors.toList());
//			if(!entityAddressListTmp.isEmpty()) {
//				customer.setCustomerAddresses(entityAddressListTmp);
//			}
//			
//			List<EntityContact> entityContactListTmp = entityContactList.stream().filter(ec -> ec.getDbEntity().getId() == customer.getDbEntity().getId()).collect(Collectors.toList());
//			if(!entityContactListTmp.isEmpty()) {
//				customer.setCustomerContacts(entityContactListTmp);
//			}
		}
			
		
		return customerList;
	}
	
	@Override
	public Customer updateCustomerAddressAndContact(Customer customer) {
		
		List<EntityAddress> entitiyAddressList = entityAddressRepository.findAll();
		List<EntityContact> entityContactList = entityContactRepository.findAll();
		
		List<EntityAddress> entityAddressListTmp = entitiyAddressList.stream().filter(ea -> ea.getDbEntity().getId() == customer.getDbEntity().getId()).collect(Collectors.toList());
		if(!entityAddressListTmp.isEmpty()) {
			customer.setCustomerAddresses(entityAddressListTmp);
		}
		
		List<EntityContact> entityContactListTmp = entityContactList.stream().filter(ec -> ec.getDbEntity().getId() == customer.getDbEntity().getId()).collect(Collectors.toList());
		if(!entityContactListTmp.isEmpty()) {
			customer.setCustomerContacts(entityContactListTmp);
		}
		
		return customer;
	}
	
	@Override
	public List<Customer> getAll() {
		
		log.info("getAll()");
		String qlString = "select c from Customer c where c.customerId != 'WalkIn '";
		List<Customer> existingList = entityManager.createQuery(qlString, Customer.class)
				.getResultList();
		
		//List<Customer> customerList = customerRepository.findAll();
		existingList = constructCustomerList(existingList);
		return existingList;
	}

	@Override
	public Customer save(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}

	@Override
	public List<CustomerDiscount> getAllCustomerDiscounts() {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		String todayNow = now.format(format);

		String qlString = "select cd from CustomerDiscount cd"
				+ " where ((cd.startDate = null) or (cd.startDate <= '" + todayNow + "'))"
				+ " and ((cd.endDate = null) or (cd.endDate > '" + todayNow + "'))";
		List<CustomerDiscount> existingCustomerDiscountList = null;
		try {
		 existingCustomerDiscountList = entityManager.createQuery(qlString, CustomerDiscount.class).getResultList();
		
		} catch(Exception ex) {
			
			return new ArrayList<CustomerDiscount>();
		}

		
		
		return existingCustomerDiscountList;
	}

}
