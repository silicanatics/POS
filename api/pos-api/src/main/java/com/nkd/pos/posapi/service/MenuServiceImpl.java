package com.nkd.pos.posapi.service;


import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.MenuItemRepository;
import com.nkd.pos.posapi.entity.MenuItem;




@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	EntityManager entityManager;
	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Override
	public List<MenuItem> findAll(int mode) {

		String qlString = "select a from MenuItem a";
		List<MenuItem> existingList = entityManager.createQuery(qlString, MenuItem.class)
				.getResultList();
		
		//List<MenuItem> ret = menuItemRepository.findAll();
		return existingList;
	}

	@Override
	public MenuItem saveNewItem(MenuItem menuItem) {
		return menuItemRepository.save(menuItem);
		//return this.findAll(0);
	}

}
