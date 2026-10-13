package com.nkd.pos.posapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkd.pos.posapi.entity.ItemOption;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.MenuItemOption;
import com.nkd.pos.posapi.entity.MenuItemPayload;
import com.nkd.pos.posapi.entity.MenuItemType;
import com.nkd.pos.posapi.service.MenuItemOptionService;
import com.nkd.pos.posapi.service.MenuItemTypeService;
import com.nkd.pos.posapi.service.MenuService;

@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {

private static final Logger log = LoggerFactory.getLogger(MenuController.class);
	
@Autowired
MenuService menuService;
@Autowired
MenuItemOptionService menuItemOptionService;
@Autowired
MenuItemTypeService menuItemTypeService;

@PostMapping(value = "/saveNewMenuItem", produces = MediaType.APPLICATION_JSON_VALUE)
public MenuItem saveNewMenuItem(@RequestBody MenuItem menuItem){

	MenuItem newMenuItem = menuService.saveNewItem(menuItem);
	//nuItemPayload = menuItemOptionService.saveMenuItem(menuItemPayload);
	 
	return  newMenuItem;
	 
}
	
	@RequestMapping(value = "/getMenus",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MenuItem> GetMenus(@RequestParam("mode") int mode) {
			
		return menuService.findAll(mode);
	}
	
	@RequestMapping(value = "/getMenuItemTypes",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MenuItemType> GetMenusItemTypes() {
			
		return menuItemTypeService.findAll();
	}
	
	@RequestMapping(value = "/getMenuItemOptions",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MenuItemOption> GetMenusItemOptions() {
			
		return menuItemOptionService.findAll();
	}
	
	@RequestMapping(value = "/getItemOptions",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItemOption> GetItemOptions() {
			
		return menuItemOptionService.findAllItemOptions();
	}
	
	
	@PostMapping(value = "/saveMenuItemOptions", produces = MediaType.APPLICATION_JSON_VALUE)
	public MenuItemPayload saveMenuItemOptions(@RequestBody MenuItemPayload menuItemPayload){
	
		menuItemPayload = menuItemOptionService.saveMenuItem(menuItemPayload);
		 
		return  menuItemPayload;
		 
	}
	
	@PostMapping(value = "/updateNode", produces = MediaType.APPLICATION_JSON_VALUE)
	public MenuItem saveMenuNode(@RequestBody MenuItem menuItem){
	
		return menuService.saveNewItem(menuItem);
		  
		 
	}
	
	@PostMapping(value = "/saveItemOption", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemOption saveItemOption(@RequestBody ItemOption itemOption){
	
		return menuItemOptionService.saveItemOption(itemOption);
	}
}
