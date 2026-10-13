package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.util.List;

public class StockInfoPayload  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	private List<StockItem> stockItemList;
	private List<StockInUse> stockInUseList;
	private List<StockStorage> stockStorageList;
	private List<StockHeld> stockHeldList;
	public StockInfoPayload() {
		super();
	}
	public StockInfoPayload(List<StockItem> stockItemList, List<StockInUse> stockInUseList,
			List<StockStorage> stockStorageList, List<StockHeld> stockHeldList) {
		super();
		this.stockItemList = stockItemList;
		this.stockInUseList = stockInUseList;
		this.stockStorageList = stockStorageList;
		this.stockHeldList = stockHeldList;
	}
	public List<StockItem> getStockItemList() {
		return stockItemList;
	}
	public void setStockItemList(List<StockItem> stockItemList) {
		this.stockItemList = stockItemList;
	}
	public List<StockInUse> getStockInUseList() {
		return stockInUseList;
	}
	public void setStockInUseList(List<StockInUse> stockInUseList) {
		this.stockInUseList = stockInUseList;
	}
	public List<StockStorage> getStockStorageList() {
		return stockStorageList;
	}
	public void setStockStorageList(List<StockStorage> stockStorageList) {
		this.stockStorageList = stockStorageList;
	}
	public List<StockHeld> getStockHeldList() {
		return stockHeldList;
	}
	public void setStockHeldList(List<StockHeld> stockHeldList) {
		this.stockHeldList = stockHeldList;
	}
	@Override
	public String toString() {
		return "StockInfoPayload [stockItemList=" + stockItemList + ", stockInUseList=" + stockInUseList
				+ ", stockStorageList=" + stockStorageList + ", stockHeldList=" + stockHeldList + "]";
	}
	
	
} 
