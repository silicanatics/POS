package com.nkd.pos.posapi.service;


import java.util.List;

import com.nkd.pos.posapi.entity.MenuItem;

public interface MenuService {

	List<MenuItem> findAll(int mode);
	MenuItem saveNewItem(MenuItem menuItem);
}
