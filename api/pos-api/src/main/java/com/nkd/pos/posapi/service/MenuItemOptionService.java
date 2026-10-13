package com.nkd.pos.posapi.service;

import java.util.List;

import com.nkd.pos.posapi.entity.ItemOption;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.MenuItemOption;
import com.nkd.pos.posapi.entity.MenuItemPayload;

public interface MenuItemOptionService {

	public List<MenuItemOption> findAll();
	public List<ItemOption> findAllItemOptions();
	public List<MenuItemOption> save(List<MenuItemOption> menuItemOptions);
	public MenuItemPayload saveMenuItem(MenuItemPayload menuItemPayload);
	public ItemOption saveItemOption(ItemOption itemOption);
}
