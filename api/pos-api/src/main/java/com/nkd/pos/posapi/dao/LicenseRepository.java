package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.License;

public interface LicenseRepository extends JpaRepository<License,Integer>  {

}
