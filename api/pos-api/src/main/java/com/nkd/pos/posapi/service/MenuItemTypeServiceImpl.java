package com.nkd.pos.posapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.MenuItemTypeRepository;
import com.nkd.pos.posapi.entity.MenuItemType;

@Service
public class MenuItemTypeServiceImpl implements MenuItemTypeService {

	@Autowired
	MenuItemTypeRepository menuItemTypeRepository;
	@Override
	public List<MenuItemType> findAll() {
		
		return menuItemTypeRepository.findAll();
	}

}
