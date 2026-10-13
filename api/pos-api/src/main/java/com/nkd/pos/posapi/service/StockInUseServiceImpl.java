package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.StockInUseRepository;
import com.nkd.pos.posapi.entity.StockInUse;
import com.nkd.pos.posapi.entity.StockItem;

@Service
public class StockInUseServiceImpl implements StockInUseService{

	@Autowired
	StockInUseRepository stockInUseRepository;
	

	@Override
	public void removeAllByStockListAndDate(List<StockItem> stockItemList, LocalDate date) {
		// TODO Auto-generated method stub
		
		
	}


	@Override
	public List<StockInUse> saveAll(List<StockInUse> stockInUseList) {
		
		return stockInUseRepository.saveAll(stockInUseList);
	}

	//
}
