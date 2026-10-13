package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.ClientPrinter;

public interface ClientPrinterRepository  extends JpaRepository<ClientPrinter, Integer> {

}
