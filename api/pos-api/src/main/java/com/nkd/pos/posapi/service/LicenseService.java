package com.nkd.pos.posapi.service;

import java.time.LocalDate;

import com.nkd.pos.posapi.entity.License;

public interface LicenseService {

	public License getLicense();
	public LocalDate getExpireryDate();
	public void updateLicenseInformation();
}
