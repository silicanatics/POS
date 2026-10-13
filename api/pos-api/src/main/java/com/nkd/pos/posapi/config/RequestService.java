package com.nkd.pos.posapi.config;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {

	String getClientIPAddress(HttpServletRequest request);
	String getClientHostName(HttpServletRequest request);
}
