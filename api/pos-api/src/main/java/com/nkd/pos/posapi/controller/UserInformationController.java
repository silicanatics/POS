package com.nkd.pos.posapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nkd.pos.posapi.config.RequestService;
import com.nkd.pos.posapi.entity.Application;
import com.nkd.pos.posapi.entity.MachinePrinter;
import com.nkd.pos.posapi.service.MachinePrinterService;


@RestController
@RequestMapping("/utils")
@CrossOrigin(origins = "*")
public class UserInformationController {

	@Autowired
	RequestService requestService;	
	@Autowired
	MachinePrinterService machinePrinterService;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@RequestMapping(value = "/getApplicationInfo",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Application> getApplicationInfo() {
			

		return machinePrinterService.getApplicationInfo();
	}
	
	@RequestMapping(value = "/getInfo",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MachinePrinter> GetMenus() {
			
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ipAddress = requestService.getClientIPAddress(request);
		String machineName = requestService.getClientHostName(request);
		log.info(ipAddress);
		log.info(machineName);
		return machinePrinterService.getByIpAddress(ipAddress);// getByMachineName(machineName);
	}
	
}
