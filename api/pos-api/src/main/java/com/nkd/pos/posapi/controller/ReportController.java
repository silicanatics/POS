package com.nkd.pos.posapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkd.pos.posapi.entity.ItemsSoldReport;
import com.nkd.pos.posapi.entityReports.AccountantReport;
import com.nkd.pos.posapi.entityReports.ChartReport;
import com.nkd.pos.posapi.entityReports.DailyPurchasesReport;
import com.nkd.pos.posapi.service.EntityReportsService;
import com.nkd.pos.posapi.service.MenuService;
import com.nkd.pos.posapi.service.ReportService;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	ReportService reportService;
	@Autowired
	EntityReportsService entityReportsService;
	
	@RequestMapping(value = "/getItemsSoldReportImage", produces = MediaType.APPLICATION_PDF_VALUE)
	public byte[] getItemsSoldReportImage(@RequestParam("PurchaseDate") String purchaseDate) {
		
		byte[] image=  reportService.getItemsSoldReport(purchaseDate);
		return image;
	}
	
	@RequestMapping(value = "/getDailyPurchasesReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public DailyPurchasesReport getDailyPurchasesReport(@RequestParam("PurchaseDate") String purchaseDate) {
		
		DailyPurchasesReport report=  entityReportsService.getDailyPurchaseReport(purchaseDate);
		//log.info(purchaseDate);
		log.info(report.toString());
		return report;
	}
	
	@RequestMapping(value = "/getItemsSoldReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItemsSoldReport> getItemsSoldReport(@RequestParam("PurchaseDate") String purchaseDate) {
		
		List<ItemsSoldReport> itemsSoldReportList =  entityReportsService.getItemsSoldReport(purchaseDate);
		//log.info(purchaseDate);
		log.info(itemsSoldReportList.toString());
		return itemsSoldReportList;
	}
	
	@RequestMapping(value = "/getAccountantReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public AccountantReport getAccountantReport(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		
		AccountantReport report=  entityReportsService.getAccountantReport(startDate, endDate);
		return report;
	}
	
	@RequestMapping(value = "/getChartsData", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ChartReport> getChartsData(@RequestParam("menuId") int menuId, @RequestParam("date") String strDate) {
		
		List<ChartReport> ret = reportService.getCharts(menuId, strDate);
		
		return ret;
		
	}
	
}
