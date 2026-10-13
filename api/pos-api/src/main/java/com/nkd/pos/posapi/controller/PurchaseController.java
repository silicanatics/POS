package com.nkd.pos.posapi.controller;


import java.io.IOException;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nkd.pos.posapi.config.RequestService;
import com.nkd.pos.posapi.entity.DeliveryCharge;
import com.nkd.pos.posapi.entity.PaymentType;
import com.nkd.pos.posapi.entity.PurchaseDetail;
import com.nkd.pos.posapi.entity.PurchaseInvoice;
import com.nkd.pos.posapi.entity.PurchasePayload;
import com.nkd.pos.posapi.entity.PurchaseState;
import com.nkd.pos.posapi.entity.PurchaseType;
import com.nkd.pos.posapi.service.ItemPurchaseService;
import com.nkd.pos.posapi.service.PurchaseInvoiceService;
import com.nkd.pos.posapi.service.PurchaseService;
import com.nkd.pos.posapi.service.ReportService;

@RestController
@RequestMapping("/purchase")
@CrossOrigin
public class PurchaseController {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	PurchaseInvoiceService purchaseInvoiceService;
	@Autowired
	ReportService reportService;
	@Autowired
	ItemPurchaseService itemPurchaseService;
	@Autowired
	RequestService requestService;

	
	//completePurchase
	@PostMapping(value ="/completePurchase", produces = MediaType.APPLICATION_JSON_VALUE)
	public void completePurchase(@RequestBody PurchasePayload purchasePayload) {

		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//logger.info(purchasePayload.toString());
		logger.info("savePurchaseItems");
	

		String ipAddress = requestService.getClientIPAddress(request);
		purchasePayload.setClientIP(ipAddress);	
		
		purchasePayload = purchaseService.completePurchase(purchasePayload);
		reportService.printReciept(purchasePayload);
		
	}
	
	
	@PostMapping(value ="/savePurchaseItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public PurchasePayload savePurchaseItems(@RequestBody PurchasePayload purchasePayload) {

		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//logger.info(purchasePayload.toString());
		logger.info("savePurchaseItems");
	
		logger.info(purchasePayload.getItemPurchase().toString());
		String ipAddress = requestService.getClientIPAddress(request);
		purchasePayload.setClientIP(ipAddress);	
		
		purchasePayload = purchaseService.savePurchase(purchasePayload);
		try {
		reportService.printReciept(purchasePayload);
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return purchasePayload;
		
	}
	
	@PostMapping(value = "/getInvoiceImage", produces = MediaType.APPLICATION_PDF_VALUE)
	public byte[] getInvoiceImage(@RequestBody PurchasePayload purchasePayload) {
		
		try {
			byte[] image=  reportService.getInvoiceImage(purchasePayload);
		//	reportService.printTest(image);
			logger.info("**getInvoiceImage**");
			logger.info(image.toString());
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	@RequestMapping(value = "/getPurchaseInvoice",produces = MediaType.APPLICATION_JSON_VALUE)
	public PurchaseInvoice getPurchaseInvoive() {
		
		return purchaseInvoiceService.getNextPurchaseInvoiceStatic();		
		
	}
	
	@RequestMapping(value = "/getPaymentTypes",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentType> getPaymentTypes() {
		
		return itemPurchaseService.getPaymentTypeList();		
		
	}
	
	@RequestMapping(value = "/getPurchaseTypes",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseType> getPurchaseTypes() {
		
		return itemPurchaseService.getPurchaseTypeList();		
		
	}
	
	@RequestMapping(value = "/getDeliveryChargeList",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeliveryCharge> getDeliveryChageList() {
		
		return itemPurchaseService.getDeliveryChargeList();
		
	}
	
	@RequestMapping(value = "/getPurchaseInvoiveTest",produces = MediaType.APPLICATION_JSON_VALUE)
	public PurchaseInvoice getPurchaseInvoiveTest() {
		
		return purchaseInvoiceService.getNextPurchaseInvoice();		
		
	}
	
	@RequestMapping(value = "/staticGetPurchaseInvoive",produces = MediaType.APPLICATION_JSON_VALUE)
	public PurchaseInvoice staticGetPurchaseInvoive() {
		
		return null; //PurchaseInvoiceService.staticGetNextPurchaseInvoice();		
		
	}
	
	
	@RequestMapping(value = "/findPurchases",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseDetail> findIncompletePurchases(@RequestParam("stateId") int stateId){
		
		return itemPurchaseService.findPurchases(stateId);
	}
	
	
	
	@RequestMapping(value = "/findPurchasesByType",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseDetail> findPurchasesByType(@RequestParam("purchaseTypeId") int purchaseTypeId, @RequestParam("date") String date ){
		
		return itemPurchaseService.findPurchasesByType(purchaseTypeId, date);
	}
	
	//ItemPurchase
	
}
