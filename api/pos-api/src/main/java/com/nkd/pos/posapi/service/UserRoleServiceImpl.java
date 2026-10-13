package com.nkd.pos.posapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.UserRoleRepository;
import com.nkd.pos.posapi.entity.UserRole;
import com.nkd.pos.posapi.entity.Usr;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public List<UserRole> findByUser(Usr user) {
		
		return userRoleRepository.findByUser(user);
	}

}
