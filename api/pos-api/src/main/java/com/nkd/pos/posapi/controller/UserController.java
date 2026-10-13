package com.nkd.pos.posapi.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nkd.pos.posapi.entity.DBEntity;
import com.nkd.pos.posapi.entity.Usr;
import com.nkd.pos.posapi.service.UserService;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	UserService userService;
	
	@PostMapping(value ="/saveUser",
	produces = MediaType.APPLICATION_JSON_VALUE)
	public Usr saveUser(@RequestBody Usr _user) {
		logger.info("saveUser");
		//purchaseService.savePurchase(purchasePayload);
		Usr user = userService.save(_user);
	
		return user;
	}

}
