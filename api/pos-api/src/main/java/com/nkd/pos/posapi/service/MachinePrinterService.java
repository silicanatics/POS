package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.Application;
import com.nkd.pos.posapi.entity.MachinePrinter;

public interface MachinePrinterService {

	public List<MachinePrinter> getByIpAddress(String ipAddress);
	public List<MachinePrinter> getByMachineName(String machineName);
	public List<MachinePrinter> getByIpAndMachineName(String ipAddress, String machineName);
	public List<Application> getApplicationInfo();
	public Application updateApplication(Application application);
}
