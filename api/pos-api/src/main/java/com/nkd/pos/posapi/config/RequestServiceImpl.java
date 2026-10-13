package com.nkd.pos.posapi.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



@Service
public class RequestServiceImpl implements RequestService {

	private final String LOCALHOST_IPV4 = "127.0.0.1";
	private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	private final String UNKNOWN = "unknown";
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public String getClientIPAddress(HttpServletRequest request) {

		
		Map<String,Object> hostInfo = new HashMap<String, Object>();
		String clientIPAddress = request.getHeader("X-Forwarded-For");
		String clientHostName = request.getHeader("referer");
		logger.info("X-Forwarded-For : " + clientIPAddress );
		logger.info("referer : " + clientHostName );
		clientHostName = request.getHeader("X-Forwarded-Host");
		logger.info("X-Forwarded-Host : " + clientHostName );
		clientHostName = request.getHeader("X-Forwarded-Host-Header");
		logger.info("X-Forwarded-Host-Header: " + clientHostName );
		InetAddress inetAddressTmp = null;
		try {
			inetAddressTmp = InetAddress.getByName(clientIPAddress);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		clientHostName = inetAddressTmp.getHostName();
		logger.info("inetAddress.getHostName(): " + clientHostName );

		if (!StringUtils.hasLength(clientIPAddress) || UNKNOWN.equals(clientIPAddress)) {
			clientIPAddress = request.getHeader("Proxy-Client-IP");
			logger.info("Proxy-Client-IP : " + clientIPAddress );
		}

		if (!StringUtils.hasLength(clientIPAddress) || UNKNOWN.equals(clientIPAddress)) {
			clientIPAddress = request.getHeader("WL-Proxy-Client-IP");
			logger.info("WL-Proxy-Client-IP: " + clientIPAddress);
		}

		if (!StringUtils.hasLength(clientIPAddress) || UNKNOWN.equals(clientIPAddress)) {
			clientIPAddress = request.getRemoteAddr();
			logger.info("request.getRemoteAddr(): " + clientIPAddress);
			if (LOCALHOST_IPV4.equals(clientIPAddress) || LOCALHOST_IPV6.equals(clientIPAddress)) {
				try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					 clientIPAddress = inetAddress.getHostAddress();
					 logger.info("InetAddress.getLocalHost(): " + clientIPAddress);
				} catch (UnknownHostException e) {
					logger.info(e.getMessage());
				}
			}

		}
		
		if (!StringUtils.hasLength(clientIPAddress) 
				&& clientIPAddress.length() > 15
				&& clientIPAddress.indexOf(",") > 0) {
			clientIPAddress = clientIPAddress.substring(0,clientIPAddress.indexOf(","));
		}

		return clientIPAddress;
	}
	
	@Override
	public String getClientHostName(HttpServletRequest request) {
		InetAddress inetAddress = null;
		String clientHostName = "";
		try {
			
			   String remoteAddr = request.getRemoteAddr();
			   
		        // client's hostname
		        String remoteHost = request.getRemoteHost();
		        
		        logger.info( "remoteAddr " + remoteAddr);
		        logger.info( "remoteHost = " + remoteHost);
		 
		        
			inetAddress = InetAddress.getLocalHost();
			//inetAddress.getAddress()
			InetAddress ia = inetAddress.getByAddress(inetAddress.getAddress());
			logger.info(inetAddress.toString());
			logger.info("******************");
			logger.info( InetAddress.getLoopbackAddress().getHostAddress());
			logger.info(   InetAddress.getLoopbackAddress().getHostName());
			logger.info( InetAddress.getLocalHost().getHostAddress());
			logger.info( InetAddress.getLocalHost().getHostName());
			
			clientHostName = inetAddress.getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientHostName;
	}

}
