package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="stock_held")
public class StockHeld  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name = "stockItemId")
	private StockItem  stockItem;
	private float openningStock;
	private float closingStock;
	private float items;
	private LocalDate date;
	public StockHeld() {
		super();
	}
	public StockHeld(int id, StockItem stockItem, float openningStock, float closingStock, float items,
			LocalDate date) {
		super();
		this.id = id;
		this.stockItem = stockItem;
		this.openningStock = openningStock;
		this.closingStock = closingStock;
		this.items = items;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StockItem getStockItem() {
		return stockItem;
	}
	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}
	public float getOpenningStock() {
		return openningStock;
	}
	public void setOpenningStock(float openningStock) {
		this.openningStock = openningStock;
	}
	public float getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(float closingStock) {
		this.closingStock = closingStock;
	}
	public float getItems() {
		return items;
	}
	public void setItems(float items) {
		this.items = items;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "StockHeld [id=" + id + ", stockItem=" + stockItem + ", openningStock=" + openningStock
				+ ", closingStock=" + closingStock + ", items=" + items + ", date=" + date + "]";
	}
		
	
}
