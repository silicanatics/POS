package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.UserPrinter;

public interface UserPrinterRepository extends JpaRepository<UserPrinter,Integer> {

}
