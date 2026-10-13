package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.MenuItemRepository;
import com.nkd.pos.posapi.dao.StockInUseRepository;
import com.nkd.pos.posapi.dao.StockItemRepository;
import com.nkd.pos.posapi.dao.StockStorageRepository;
import com.nkd.pos.posapi.entity.Application;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.PurchaseDetail;
import com.nkd.pos.posapi.entity.QuantityType;
import com.nkd.pos.posapi.entity.SelectedItemPurchaseDetail;
import com.nkd.pos.posapi.entity.StockInUse;
import com.nkd.pos.posapi.entity.StockInfoPayload;
import com.nkd.pos.posapi.entity.StockItem;
import com.nkd.pos.posapi.entity.StockStorage;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	EntityManager entityManager;
	@Autowired
	ItemPurchaseDetailService itemPurchaseDetailService;
	@Autowired
	ItemPurchaseService itemPurchaseService;
	@Autowired
	StockStorageRepository stockStorageRepository;
	@Autowired
	StockInUseService stockInUseService;
	@Autowired
	StockItemRepository stockItemRepository;
	@Autowired
	StockInUseRepository stockInUseRepository;
	@Autowired
	MenuItemRepository menuItemRepository;

	MenuItem previousMenuItem = new MenuItem();
	Application application = null;

	@Override
	public List<StockItem> getStockItems() {

		return stockItemRepository.findAll();
	}

	@Override
	public List<StockStorage> getStockStorage() {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		String todayNow = now.format(format);
		String qlString = "select ss from StockStorage ss" + " where ((ss.startDate = null) or (ss.startDate <= '"
				+ todayNow + "'))" + " and ((ss.endDate = null) or (ss.endDate > '" + todayNow + "'))";
		List<StockStorage> existingStockStorageList = entityManager.createQuery(qlString, StockStorage.class)
				.getResultList();
		return existingStockStorageList;

	}

	@Override
	public List<StockInfoPayload> getStockInfo(String date) {

		LocalDate today = LocalDate.now();
		// String date = String.valueOf(today.getYear()) + "/" +
		// String.valueOf(today.getMonthValue()) + "/" +
		// String.valueOf(today.getDayOfMonth());
		List<PurchaseDetail> purchaseDetailList = itemPurchaseService.findPurchasesByType(0, date);

		List<ItemPurchaseDetail> itemPurchaseDetailList = getItemPurchaseDetailList(purchaseDetailList);

		List<StockItem> stockList = getStockList(itemPurchaseDetailList);

		List<StockInUse> stockInUseList = getStockInUse(stockList);

		updateStockInUse(itemPurchaseDetailList, stockList, stockInUseList);
		return null;
	}

	void updateStockInUse(List<ItemPurchaseDetail> itemPurchaseDetailList, List<StockItem> stockItemList,
			List<StockInUse> stockInUseList) {

		for (StockInUse siu : stockInUseList) {

			for (StockItem si : stockItemList) {

				for (ItemPurchaseDetail ipd : itemPurchaseDetailList) {

					if (ipd.getMenuItem().getId() == si.getMenuItem().getId()
							&& si.getId() == siu.getStockItem().getId()) {

						siu.setStock(siu.getStock() - ipd.getItems());
					}
				}
			}
		}

		stockInUseService.saveAll(stockInUseList);

	}

	private void updateStockInUse(String strDate, List<ItemPurchaseDetail> itemPurchaseDetailList,
			List<StockItem> stockList) {

		LocalDate localDate = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		Iterator<StockItem> stockItemIter = stockList.iterator();

		while (stockItemIter.hasNext()) {

		}

	}

	// List<StockStorage> stockStorageList
	void updateStockStorage(List<ItemPurchaseDetail> itemPurchaseDetailList, List<StockItem> stockItemList,
			List<StockStorage> stockStorageList) {

		for (StockStorage siu : stockStorageList) {

			for (StockItem si : stockItemList) {

				for (ItemPurchaseDetail ipd : itemPurchaseDetailList) {

					if (ipd.getMenuItem().getId() == si.getMenuItem().getId()
							&& si.getId() == siu.getStockItem().getId()) {

						siu.setQuantityLeft(siu.getQuantityLeft() - ipd.getItems());
						siu.setSoldQuantity(siu.getSoldQuantity() + ipd.getItems());
						float profit = (siu.getItemResellPrice() - siu.getItemCostPrice()) * ipd.getItems();
						siu.setProfit(siu.getProfit() + profit);
					}
				}
			}
		}

		stockStorageRepository.saveAll(stockStorageList);

	}

	private List<StockInUse> getStockInUse(List<StockItem> stockList) {

		List<Integer> iDs = new ArrayList<Integer>();
		for (StockItem si : stockList) {
			iDs.add(si.getId());
		}
		String result = iDs.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", ""));

		String qlString = "select siu from StockInUse siu where siu.stockItem.id in (" + result + ")";
		List<StockInUse> existingStockInUseList = entityManager.createQuery(qlString, StockInUse.class).getResultList();

		return existingStockInUseList;

	}

	private List<StockStorage> getStockStorageList(List<StockItem> stockList) {

		List<Integer> iDs = new ArrayList<Integer>();
		for (StockItem si : stockList) {
			iDs.add(si.getId());
		}
		String result = iDs.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", ""));

		String qlString = "select ss from StockStorage ss where ss.stockItem.id in (" + result + ")";
		List<StockStorage> existingStockStorageList = entityManager.createQuery(qlString, StockStorage.class)
				.getResultList();

		return existingStockStorageList;

	}

	private List<ItemPurchaseDetail> getItemPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {

		List<ItemPurchaseDetail> itemPurchaseDetailList = new ArrayList<ItemPurchaseDetail>();

		for (PurchaseDetail pd : purchaseDetailList) {

			itemPurchaseDetailList.addAll(pd.getItemPurchaseDetailList());
		}

		return itemPurchaseDetailList;
	}

	private List<StockItem> getStockList(List<ItemPurchaseDetail> itemPurchaseDetailList) {

		List<Integer> iDs = new ArrayList<Integer>();

		Iterator<ItemPurchaseDetail> itemPurchaseDetailIter = itemPurchaseDetailList.iterator();
		while (itemPurchaseDetailIter.hasNext()) {

			ItemPurchaseDetail ipd = (ItemPurchaseDetail) itemPurchaseDetailIter.next();
			iDs.add(ipd.getId());
		}

		String result = iDs.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", ""));

		String qlString = "select pd from ItemPurchaseDetail pd where pd.id in (" + result + ")";
		List<ItemPurchaseDetail> existingItemPurchaseDetailList = entityManager
				.createQuery(qlString, ItemPurchaseDetail.class).getResultList();

		iDs = new ArrayList<Integer>();
		for (ItemPurchaseDetail ip : existingItemPurchaseDetailList) {
			iDs.add(ip.getMenuItem().getId());
		}

		result = iDs.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", ""));

		qlString = "select si from StockItem si where si.menuItem.id in (" + result + ")";

		List<StockItem> existingStockItemList = entityManager.createQuery(qlString, StockItem.class).getResultList();

		return existingStockItemList;

	}

	@Override
	public void deleteStockStorageByStockListAndDate(List<StockItem> stockItemList, LocalDate date) {

		String result = stockItemList.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", ""));

		// stockStorageRepository.

	}

	@Override
	public void updateStockInUse(List<ItemPurchaseDetail> itemPurchaseDetailList) {

		List<StockItem> stockList = getStockList(itemPurchaseDetailList);

		if (stockList.isEmpty())
			return;

		List<StockInUse> stockInUseList = getStockInUse(stockList);
		List<StockStorage> stockStorageList = getStockStorageList(stockList);

		updateStockInUse(itemPurchaseDetailList, stockList, stockInUseList);
		updateStockStorage(itemPurchaseDetailList, stockList, stockStorageList);

	}

	@Override
	public StockItem saveStockItem(StockItem stockItem) {

		return stockItemRepository.save(stockItem);
	}

	@Override
	public void updateAllStockItems() {

		String qlString = "select a from Application a ";
		application = entityManager.createQuery(qlString, Application.class).getSingleResult();

		List<StockItem> currentStockItemList = stockItemRepository.findAll();
		LocalDateTime today = LocalDateTime.now();
		Date dateToday = java.sql.Timestamp.valueOf(today);
		int initialQuantity = 0;

		qlString = "select mi from MenuItem mi where mi.price != 0";
		List<MenuItem> menuItemList = entityManager.createQuery(qlString, MenuItem.class).getResultList();
		
		qlString = "select mi from MenuItem mi";
		List<MenuItem> menuItemAllList = entityManager.createQuery(qlString, MenuItem.class).getResultList();

		List<StockItem> stockItemList = new ArrayList<StockItem>();
		
		List<StockItem> siList = new ArrayList<StockItem>();
		List<StockStorage> ssList = new ArrayList<StockStorage>();
		List<StockInUse> siuList = new ArrayList<StockInUse>();
		
		for (MenuItem mi : menuItemList) {

			if (!containsMenuItem(currentStockItemList, mi)) {
				StockItem si = new StockItem();
				
				si.setCaption(getStockStorageCaption(menuItemAllList, mi));
				QuantityType qt = new QuantityType();
				qt.setId(1);
				si.setMenuItem(mi);
				si.setQuantityType(qt);
				si.setTotalQuantity(0);				
				siList.add(si);
			
			}
		}
		
		siList = stockItemRepository.saveAllAndFlush(siList);

		for (StockItem si : siList) {	
				

				StockStorage ss = new StockStorage();
				ss.setStockItem(si);
				ss.setCurrent(true);
				ss.setDateCaptured(dateToday);
				ss.setEndDate(null);
				ss.setInitialQuantity(initialQuantity);
				if (si.getMenuItem().getPrice() > 0) // getNodeType().getId() == 4) {
				{
					ss.setPrice(new Float(si.getMenuItem().getPrice() * initialQuantity * 1.1));
					ss.setItemResellPrice((float)si.getMenuItem().getPrice());
				}
				ss.setQuantityLeft(initialQuantity);
				ss.setSequence(0);
				ss.setSoldQuantity(0);
				ss.setStartDate(dateToday);
				ssList.add(ss);
		}
		ssList = stockStorageRepository.saveAllAndFlush(ssList);
		
		for (StockStorage ss : ssList) {	
				//ss = stockStorageRepository.save(ss);
				StockInUse siu = new StockInUse();
				siu.setStockItem(ss.getStockItem());
				siu.setStockStorage(ss);
				siu.setStock(ss.getInitialQuantity());
				siuList.add(siu);
				
		}
		siuList = stockInUseRepository.saveAllAndFlush(siuList);

	

	}

	private String getStockStorageCaption(List<MenuItem> menuItemList, MenuItem mi) {
		
		 String menuItemCaption = mi.getCaption();
		MenuItem parentItem = getParentItem(menuItemList, mi);
       
		return parentItem.getCaption() + "-" + menuItemCaption;
	}

	MenuItem getParentItem(List<MenuItem> menuItemList, MenuItem menuItem) {

		MenuItem parent = null;

		parent = getMenuItemParent(menuItemList, menuItem);

		return parent;

	}

	MenuItem getMenuItemParent(List<MenuItem> menuItemList, MenuItem menuItem) {

		MenuItem parent = null;

		List<MenuItem> menuItemListTmp = new ArrayList<MenuItem>(); // = menuItemList.stream().filter(mi -> mi.getId()
																	// ==
																	// menuItem.getLinkedId()).collect(Collectors.toList());
		for (MenuItem mi : menuItemList) {
			if (mi.getId() == menuItem.getLinkedId()) {
				menuItemListTmp.add(mi);
			}
		}
		if (menuItemListTmp.size() > 0) {
			this.previousMenuItem = menuItem;
			parent = getMenuItemParent(menuItemList, menuItemListTmp.get(0));

		} else {
			if (this.application.getId() == 4) {
				//this.previousMenuItem.setCaption(menuItem.getCaption() + "-" + this.previousMenuItem.getCaption());
				menuItem = this.previousMenuItem; // 2023-11-29. Just before Primary-School and Secondary Schhol
			}
			parent = menuItem;
		}
		return parent;
	}

	private boolean containsMenuItem(List<StockItem> currentStockItemList, MenuItem mi) {
		boolean ret = false;

		for (StockItem item : currentStockItemList) {

			if (item.getMenuItem().getId() == mi.getId()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	@Override
	public StockStorage saveStockStorage(StockStorage stockStorage) {

		StockStorage updatedStockStorage = null;
		MenuItem updatedMenuItem = null;
		int miId = stockStorage.getStockItem().getMenuItem().getId();
		updatedMenuItem = menuItemRepository.getById(miId);
		if (!updatedMenuItem.getCaption().equals(stockStorage.getStockItem().getMenuItem().getCaption())) {
			updatedMenuItem.setCaption(stockStorage.getStockItem().getMenuItem().getCaption());
		} else {
			updatedMenuItem = null;
		}
		LocalDateTime now = LocalDateTime.now();
		Date dateToday = java.sql.Timestamp.valueOf(now);
//		   DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");  
//		     String todayNow = now.format(format);
		if (stockStorage.getStockItem().getMenuItem().getPrice() != stockStorage.getItemResellPrice()) {
			updatedStockStorage = stockStorageRepository.getById(stockStorage.getId());
			updatedStockStorage.setEndDate(dateToday);
			stockStorage.setId(0);
			stockStorage.setStartDate(dateToday);
			updatedMenuItem = menuItemRepository.getById(miId);
			updatedMenuItem.setPrice(stockStorage.getItemResellPrice());

		}

		if (updatedMenuItem != null) {
			menuItemRepository.saveAndFlush(updatedMenuItem);
		}

		if (updatedStockStorage != null) {
			stockStorageRepository.saveAndFlush(updatedStockStorage);
		}

		return stockStorageRepository.save(stockStorage);
	}

}
