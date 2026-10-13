package com.nkd.pos.posapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.PrinterDestinationRepository;
import com.nkd.pos.posapi.entity.PrinterDestination;

@Service
public class PrinterDestinationServiceImpl implements PrinterDestinationService {

	@Autowired
	PrinterDestinationRepository printerDestinationRepository;
	
	@Override
	public List<PrinterDestination> findAll() {
		// TODO Auto-generated method stub
		return printerDestinationRepository.findAll();
	}

}
