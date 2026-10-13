package com.nkd.pos.posapi.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.ApplicationRepository;
import com.nkd.pos.posapi.dao.MachinePrinterRepository;
import com.nkd.pos.posapi.entity.Application;
import com.nkd.pos.posapi.entity.MachinePrinter;
import com.nkd.pos.posapi.entity.MenuItemOption;

@Service
public class MachinePrinterServiceImpl implements MachinePrinterService {
    @Autowired
	MachinePrinterRepository machinePrinterRepository;
	@Autowired
	EntityManager entityManager;
	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public List<MachinePrinter> getByIpAddress(String ipAddress) {

		String qlString = "select mp from MachinePrinter mp"
				+ " where mp.ipAddress = '" + ipAddress + "'";
		List<MachinePrinter> machinePrinterList = entityManager
				.createQuery(qlString, MachinePrinter.class).getResultList();
		
		return machinePrinterList;
	}

	@Override
	public List<MachinePrinter> getByIpAndMachineName(String ipAddress, String machineName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MachinePrinter> getByMachineName(String machineName) {

		String qlString = "select mp from MachinePrinter mp"
				+ " where mp.machineName = '" + machineName + "'";
		List<MachinePrinter> machinePrinterList = entityManager
				.createQuery(qlString, MachinePrinter.class).getResultList();
		
		return machinePrinterList;
	}

	@Override
	public List<Application> getApplicationInfo() {

		String qlString = "select a from Application a";
				
		List<Application> applications = entityManager
				.createQuery(qlString, Application.class).getResultList();
		
		return applications;

	}

	@Override
	public Application updateApplication(Application application) {
		// TODO Auto-generated method stub
		return applicationRepository.save(application);
	}
    
    
}
