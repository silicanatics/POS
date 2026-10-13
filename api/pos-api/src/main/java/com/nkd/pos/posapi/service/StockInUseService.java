package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.util.List;

import com.nkd.pos.posapi.entity.StockInUse;
import com.nkd.pos.posapi.entity.StockItem;

public interface StockInUseService {

	public void removeAllByStockListAndDate(List<StockItem> stockItemList,  LocalDate date);
	public List<StockInUse> saveAll(List<StockInUse> stockInUseList);
}
