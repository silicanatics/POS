package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.UserRole;
import com.nkd.pos.posapi.entity.Usr;

public interface UserRoleService {

	List<UserRole> findByUser(Usr user);
	
}
