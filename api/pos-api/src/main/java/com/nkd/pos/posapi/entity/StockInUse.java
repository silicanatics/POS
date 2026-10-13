package com.nkd.pos.posapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="StockInUse")
public class StockInUse  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name = "stockStorageId")
	private StockStorage  stockStorage;
	
	@ManyToOne
	@JoinColumn(name = "stockItemId")
	private StockItem  stockItem;
	
	private float stock;
	public StockInUse() {
		super();
	}
	public StockInUse(int id, StockStorage stockStorage, float stock) {
		super();
		this.id = id;
		this.stockStorage = stockStorage;
		this.stock = stock;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StockStorage getStockStorage() {
		return stockStorage;
	}
	public void setStockStorage(StockStorage stockStorage) {
		this.stockStorage = stockStorage;
	}
	public float getStock() {
		return stock;
	}
	public void setStock(float stock) {
		this.stock = stock;
	}
	
	
	public StockItem getStockItem() {
		return stockItem;
	}
	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}
	@Override
	public String toString() {
		return "StockInUse [id=" + id + ", stockStorage=" + stockStorage + ", stock=" + stock + "]";
	}

	
	
}
