package com.nkd.pos.posapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.nkd.pos.posapi.dao.ClientPrinterRepository;
import com.nkd.pos.posapi.entity.ClientPrinter;

@Service
public class ClientPrinterServiceImpl implements ClientPrinterService {

	@Autowired
	ClientPrinterRepository clientPrinterRepository;
	
	@Override
	public List<ClientPrinter> findAll() {
		// TODO Auto-generated method stub
		return clientPrinterRepository.findAll();
	}

}
