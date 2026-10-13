package com.nkd.pos.posapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkd.pos.posapi.entity.EntityType;

public interface EntityTypeRepository extends JpaRepository<EntityType, Integer> {

}
