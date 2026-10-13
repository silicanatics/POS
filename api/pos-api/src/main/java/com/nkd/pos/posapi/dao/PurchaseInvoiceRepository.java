package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nkd.pos.posapi.entity.PurchaseInvoice;

public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice,Long> {
	@Query(value = "SELECT max(p.invoiceNumber) FROM PurchaseInvoice p ")
	public Long getMaxInvoiceNumber();
	
	@Query(value = "SELECT p1 FROM PurchaseInvoice p1 where p1.invoiceNumber = (SELECT max(p2.invoiceNumber) FROM PurchaseInvoice p2 ) ")
	public PurchaseInvoice getMaxInvoice();
}
