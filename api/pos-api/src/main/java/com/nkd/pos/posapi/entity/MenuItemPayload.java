package com.nkd.pos.posapi.entity;

import java.io.Serializable;
import java.util.List;

public class MenuItemPayload  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<MenuItem> menuItems;
	private List<MenuItemOption>  menuItemOptions;
	
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public List<MenuItemOption> getMenuItemOptions() {
		return menuItemOptions;
	}
	public void setMenuItemOptions(List<MenuItemOption> menuItemOptions) {
		this.menuItemOptions = menuItemOptions;
	}
	
	
}
