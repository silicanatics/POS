package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {

}
