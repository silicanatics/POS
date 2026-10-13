package com.nkd.pos.posapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.UserPrinterRepository;
import com.nkd.pos.posapi.entity.UserPrinter;
@Service
public class UserPrinterServiceImpl implements UserPrinterService {

	@Autowired
	UserPrinterRepository userPrinterRepository;
	@Override
	public List<UserPrinter> findAll() {
		// TODO Auto-generated method stub
		List<UserPrinter> ret= userPrinterRepository.findAll();
		return ret;
		
	}

}
