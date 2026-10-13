package com.nkd.pos.posapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nkd.pos.posapi.dao.LicenseRepository;
import com.nkd.pos.posapi.entity.Application;
import com.nkd.pos.posapi.entity.License;

@Service
public class LicenseServiceImpl implements LicenseService {

	@Autowired
	MachinePrinterService machinePrinterService;
	@Autowired
	LicenseRepository licenseRepository;
	@Autowired
	private LicenseService licenseService;
	@Override
	public License getLicense() {		
		return licenseRepository.getById(1);
	}
	@Override
	public LocalDate getExpireryDate() {
		License license = getLicense();
		String licenseStr = license.getLicense();
		Base64.Decoder decoder = Base64.getDecoder();
		String dStr = new String(decoder.decode(licenseStr));
		String dateStr = dStr.substring(dStr.indexOf(":")+1);
		
		LocalDate expireryDate = LocalDate.parse(dateStr);
		return expireryDate;
	}
	
	public String getSerialNumber() {
		License license = getLicense();
		String licenseStr = license.getLicense();
		Base64.Decoder decoder = Base64.getDecoder();
		String dStr = new String(decoder.decode(licenseStr));
		String sn = dStr.substring(0, dStr.indexOf(":"));

		return sn;
	}
	
	@Override
	public void updateLicenseInformation() {
		
		List<Application> applicationList = machinePrinterService.getApplicationInfo();
		if(!applicationList.isEmpty()) {
			Application application = applicationList.get(0);
			LocalDate expireryDate = getExpireryDate();
			application.setLicenseExpireryDate(expireryDate);
			application.setLicenseExpired(getLicenseExpirery());
			long daysBetween = Duration.between(LocalDate.now().atStartOfDay(), expireryDate.atStartOfDay()).toDays();
			application.setDaysToExpirery(daysBetween);
			
			String storedSN = getSerialNumber();
			String sn = getMachineSerialNumber();
			
			if(!storedSN.equals(sn)) { //License tempered with
				application.setLicenseExpired(true);
				application.setDaysToExpirery(0L);
			}
			machinePrinterService.updateApplication(application);
		}
		

		
	}
	
	private String getMachineSerialNumber() {
		
		String result = null;
        Process p;
		try {
			p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
			
	       BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while ((line = input.readLine()) != null) {
	            result += line;
	        }
	        result = result.substring(result.indexOf("SerialNumber") + "SerialNumber".length()).trim();
		        
		} catch (IOException e) {
			
			e.printStackTrace();
		}
 
		
		return result;
	}
	

	private Boolean getLicenseExpirery() {

		LocalDate date = licenseService.getExpireryDate();
		return LocalDate.now().isAfter(date);
	}

	


}
