package com.nkd.pos.posapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.UserRole;
import com.nkd.pos.posapi.entity.Usr;

public interface UserRoleRepository extends JpaRepository <UserRole,Integer>{

	public List<UserRole> findByUser(Usr user);
}
