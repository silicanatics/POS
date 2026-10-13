package com.nkd.pos.posapi.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.controller.CustomerController;
import com.nkd.pos.posapi.dao.ItemOptionRepository;
import com.nkd.pos.posapi.dao.MenuItemOptionRepository;
import com.nkd.pos.posapi.dao.MenuItemRepository;
import com.nkd.pos.posapi.entity.ItemOption;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.MenuItemOption;
import com.nkd.pos.posapi.entity.MenuItemPayload;

@Service
public class MenuItemOptionServiceImpl implements MenuItemOptionService {

	@Autowired
	MenuItemOptionRepository menuItemOptionRepository;
	@Autowired
	MenuItemRepository menuItemRepository;
	@Autowired
	ItemOptionRepository itemOptionRepository;
	@Autowired
	EntityManager entityManager;

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Override
	public List<MenuItemOption> findAll() {
		
		LocalDateTime now = LocalDateTime.now();
		
		
		
	     DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");  
	     String todayNow = now.format(format);  
		
		String qlString = "select mio from MenuItemOption mio"
				+ " where ((mio.startDate = null) or (mio.startDate <= '" +  todayNow + "'))"
		        + " and ((mio.endDate = null) or (mio.endDate > '" +  todayNow + "'))";
		List<MenuItemOption> existingMenuItemOptionList = entityManager
				.createQuery(qlString, MenuItemOption.class).getResultList();
		
		//return menuItemOptionRepository.findAll();
		return existingMenuItemOptionList;
	}

	@Override
	public List<ItemOption> findAllItemOptions() {

		return itemOptionRepository.findAll();
	}

	@Override
	public List<MenuItemOption> save(List<MenuItemOption> menuItemOptions) {

		List<MenuItemOption> ret = menuItemOptionRepository.saveAll(menuItemOptions);

		return ret;
	}

	@Override
	public ItemOption saveItemOption(ItemOption itemOption) {

		return itemOptionRepository.save(itemOption);
	}

	@Override
	public MenuItemPayload saveMenuItem(MenuItemPayload menuItemPayload) {

		LocalDateTime endDate = LocalDateTime.now();
		Date dateToday = java.sql.Timestamp.valueOf(endDate);
		List<MenuItem> menuItems = menuItemPayload.getMenuItems();
		this.removeMenuItemOptions(menuItems,dateToday);

		log.info(menuItems.toString());

		for (MenuItem menuItem : menuItems) {

			//if(menuItems.size() == 1)
			menuItem = menuItemRepository.save(menuItem);

			List<MenuItemOption> menuItemOptions = menuItemPayload.getMenuItemOptions();
			List<MenuItemOption> newMenuItemOptions = new ArrayList<MenuItemOption>();
			for (MenuItemOption mio : menuItemOptions) {
				MenuItemOption menuItemOption = new MenuItemOption();
				menuItemOption.setDefaultOption(mio.isDefaultOption());
				menuItemOption.setItemOption(mio.getItemOption());
				menuItemOption.setMenuItem(menuItem);
				menuItemOption.setStartDate(dateToday);
				newMenuItemOptions.add(menuItemOption);
			}

			this.save(newMenuItemOptions);

	

		}
		return menuItemPayload;
	}
	
	
	private void removeMenuItemOptions(List<MenuItem> menuItems, Date endDate) {
		
		
		
		for (MenuItem menuItem : menuItems) {

			
			String qlString = "select mio from MenuItemOption mio"
					+ " where mio.menuItem.id = " + menuItem.getId()
					+ " and ((mio.startDate = null) or (mio.startDate <= '" +  endDate + "'))"
			        + " and ((mio.endDate = null) or (mio.endDate > '" +  endDate + "'))";

			List<MenuItemOption> existingMenuItemOptionList = entityManager
					.createQuery(qlString, MenuItemOption.class).getResultList();
			
			
			for(MenuItemOption mio : existingMenuItemOptionList) {
				mio.setEndDate(endDate);
			}
			
			
			menuItemOptionRepository.saveAllAndFlush(existingMenuItemOptionList);
			
		}
	}
	
}
