package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.PrinterDestination;

public interface PrinterDestinationRepository extends JpaRepository<PrinterDestination,Integer> {

}
