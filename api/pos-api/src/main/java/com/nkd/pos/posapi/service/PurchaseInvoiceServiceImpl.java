package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.PurchaseInvoiceRepository;
import com.nkd.pos.posapi.entity.PurchaseInvoice;
@Service
public class PurchaseInvoiceServiceImpl implements PurchaseInvoiceService {

	@Value("${spring.datasource.username}")
    private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String conUrl;
	
	@Autowired
	PurchaseInvoiceRepository purchaseInvoiceRepository;
	ReentrantLock lock = new ReentrantLock();
	@Override 
	public PurchaseInvoice savePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		
		return purchaseInvoiceRepository.save(purchaseInvoice);
	}
	@Override
	public Long getMaxInvoiceNumber() {
		
		return purchaseInvoiceRepository.getMaxInvoiceNumber();
	}
	@Override
	public PurchaseInvoice getMaxInvoice() {
		// TODO Auto-generated method stub
		return purchaseInvoiceRepository.getMaxInvoice();
	}
	@SuppressWarnings("finally")
	@Override
	public PurchaseInvoice getNextPurchaseInvoice() {
		PurchaseInvoice pi = new PurchaseInvoice();
		lock.lock();
		try {
		
		pi.setInvoiceNumber(getMaxInvoiceNumber() + 1);
		PurchaseInvoice maxInvoice = purchaseInvoiceRepository.getMaxInvoice();
		LocalDate maxInvoiceDate =  new java.sql.Date(maxInvoice.getInvoiceDate().getTime()).toLocalDate();
		LocalDate currentDate = LocalDate.now();
		LocalDateTime currentDateTime = LocalDateTime.now();
		if(!currentDate.equals(maxInvoiceDate)) {
			maxInvoice.setPurchaseNumber(0);
		}
		pi.setInvoiceNumber(maxInvoice.getInvoiceNumber()+1);
		pi.setPurchaseNumber(maxInvoice.getPurchaseNumber()+1);
		
		pi.setInvoiceDate(java.sql.Date.valueOf(currentDate));
		pi.setInvoiceDateTime(java.sql.Timestamp.valueOf(currentDateTime));
		pi = purchaseInvoiceRepository.saveAndFlush(pi);
		}
		finally {
            lock.unlock();
            return pi;
        }
		
		
	}
	@Override
	public PurchaseInvoice getNextPurchaseInvoiceStatic() {
		PurchaseInvoice purchaseInvoice = PurchaseInvoiceService.staticGetNextPurchaseInvoice(userName,password,conUrl);
		purchaseInvoice = this.savePurchaseInvoice(purchaseInvoice);
		return purchaseInvoice;
	}

	
}
