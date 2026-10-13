package com.nkd.pos.posapi.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;


public class AuditorAwareImpl implements AuditorAware<String> {

	   @Override
	    public Optional<String> getCurrentAuditor(){
		   
		   String userName = SecurityUtils.getCurrentUserLogin();
		    return Optional.of(userName);		    
		    
	
		     
	    }
}
