package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nkd.pos.posapi.config.Auditable;


@Entity
@Table(name="StockStorage")
public class StockStorage extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "stockItemId")
	private StockItem  stockItem;
	
	private java.util.Date dateCaptured;
	private java.util.Date startDate;
	private float initialQuantity;
	@Column(nullable = true)
	private java.util.Date endDate;
	@Column(nullable = true)
	private float soldQuantity;
	@Column(nullable = true)
	private float quantityLeft;
	private boolean current;
	private float price;
	private float itemCostPrice;
	private float itemResellPrice;
	private float profit;
	@Column(nullable = true)
	private int sequence;
//	@ManyToOne
//	@JoinColumn(name = "businessDocumentId")
//	private BusinessDocument businessDocument;
	public StockStorage() {
		super();
	}


	public StockStorage(int id, StockItem stockItem, Date dateCaptured, Date startDate, float initialQuantity,
			Date endDate, float soldQuantity, float quantityLeft, boolean current, float price, float itemCostPrice,
			float itemResellPrice, float profit, int sequence) {
		super();
		this.id = id;
		this.stockItem = stockItem;
		this.dateCaptured = dateCaptured;
		this.startDate = startDate;
		this.initialQuantity = initialQuantity;
		this.endDate = endDate;
		this.soldQuantity = soldQuantity;
		this.quantityLeft = quantityLeft;
		this.current = current;
		this.price = price;
		this.itemCostPrice = itemCostPrice;
		this.itemResellPrice = itemResellPrice;
		this.profit = profit;
		this.sequence = sequence;
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
	
	public java.util.Date getDateCaptured() {
		return dateCaptured;
	}


	public void setDateCaptured(java.util.Date dateCaptured) {
		this.dateCaptured = dateCaptured;
	}


	public java.util.Date getStartDate() {
		return startDate;
	}


	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}


	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}


	public float getInitialQuantity() {
		return initialQuantity;
	}
	public void setInitialQuantity(float initialQuantity) {
		this.initialQuantity = initialQuantity;
	}
	
	public java.util.Date getEndDate() {
		return endDate;
	}


	public float getSoldQuantity() {
		return soldQuantity;
	}
	public void setSoldQuantity(float soldQuantity) {
		this.soldQuantity = soldQuantity;
	}
	public float getQuantityLeft() {
		return quantityLeft;
	}
	public void setQuantityLeft(float quantityLeft) {
		this.quantityLeft = quantityLeft;
	}
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public float getItemCostPrice() {
		return itemCostPrice;
	}

	public void setItemCostPrice(float itemCostPrice) {
		this.itemCostPrice = itemCostPrice;
	}

	public float getItemResellPrice() {
		return itemResellPrice;
	}

	public void setItemResellPrice(float itemResellPrice) {
		this.itemResellPrice = itemResellPrice;
	}
	
	
	public float getProfit() {
		return profit;
	}


	public void setProfit(float profit) {
		this.profit = profit;
	}


	@Override
	public String toString() {
		return "StockStorage [id=" + id + ", stockItem=" + stockItem + ", dateCaptured=" + dateCaptured + ", startDate="
				+ startDate + ", initialQuantity=" + initialQuantity + ", endDate=" + endDate + ", soldQuantity="
				+ soldQuantity + ", quantityLeft=" + quantityLeft + ", current=" + current + ", price=" + price
				+ ", itemCostPrice=" + itemCostPrice + ", itemResellPrice=" + itemResellPrice + ", sequence=" + sequence
				+ "]";
	}


}
