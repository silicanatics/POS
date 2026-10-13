package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.DBEntity;

public interface EntitiyRepository extends JpaRepository<DBEntity, Integer> {

}
