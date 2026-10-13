package com.nkd.pos.posapi.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.CustomerDiscountRepository;
import com.nkd.pos.posapi.entity.CustomerDiscount;

@Service
public class CustomerDiscountServiceImpl implements CustomerDiscountService {

	@Autowired
	private CustomerDiscountRepository customerDiscountRepository;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public CustomerDiscount save(CustomerDiscount customerDiscount) {
		// TODO Auto-generated method stub
		try {
			return customerDiscountRepository.saveAndFlush(customerDiscount);
		} catch(Exception ex) {
			logger.info("Error in CustomerDiscountServiceImpl " + ex.toString() );
		}
		
		return null;
	}

}
