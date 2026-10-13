package com.nkd.pos.posapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.Usr;

public interface UserRepository extends JpaRepository<Usr,Integer>{

	public Optional<Usr> findByusername(String username);
}
