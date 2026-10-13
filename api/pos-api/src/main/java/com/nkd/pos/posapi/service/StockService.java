package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.util.List;

import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.SelectedItemPurchaseDetail;
import com.nkd.pos.posapi.entity.StockInfoPayload;
import com.nkd.pos.posapi.entity.StockItem;
import com.nkd.pos.posapi.entity.StockStorage;

public interface StockService {

	public List<StockItem> getStockItems();
	public List<StockStorage> getStockStorage();
	public StockStorage saveStockStorage(StockStorage stockStorage);
	public StockItem saveStockItem(StockItem stockItem);
	public List<StockInfoPayload> getStockInfo(String date);
	public void deleteStockStorageByStockListAndDate(List<StockItem> stockItemList,  LocalDate date);
	public void updateStockInUse(List<ItemPurchaseDetail> itemPurchaseDetailList);
	
	public void updateAllStockItems();
	
}
