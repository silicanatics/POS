package com.nkd.pos.posapi.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkd.pos.posapi.entity.StockInfoPayload;
import com.nkd.pos.posapi.entity.StockItem;
import com.nkd.pos.posapi.entity.StockStorage;
import com.nkd.pos.posapi.entityReports.DailyPurchasesReport;
import com.nkd.pos.posapi.service.StockService;



@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private StockService stockService;
	
	
	
	@RequestMapping(value = "/getStockItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockItem> getStockItems() {
		
		return stockService.getStockItems();
	}
	
	@RequestMapping(value = "/getStockStorage", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockStorage> getStockStorage() {
		
		return stockService.getStockStorage();
	}
	
	@RequestMapping(value = "/getStockInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockInfoPayload> getStockInfo(@RequestParam("date") String date) {
		
		return stockService.getStockInfo(date);
	}
	
	@RequestMapping(value = "/updateAllStockItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateAllStockItems() {
		
		stockService.updateAllStockItems();
	}

	@PostMapping(value = "/saveStockItem", produces = MediaType.APPLICATION_JSON_VALUE)
	public StockItem saveStockItem(@RequestBody StockItem stockItem) {
		
		return stockService.saveStockItem(stockItem);
	}
	
	@PostMapping(value = "/saveStockStorageItem", produces = MediaType.APPLICATION_JSON_VALUE)
	public StockStorage saveStockItem(@RequestBody StockStorage stockStorage) {
		
		
		return stockService.saveStockStorage(stockStorage);
	}
	
	
	
}
