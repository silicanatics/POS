package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.MachinePrinter;

public interface MachinePrinterRepository extends JpaRepository<MachinePrinter, Integer>{

}
