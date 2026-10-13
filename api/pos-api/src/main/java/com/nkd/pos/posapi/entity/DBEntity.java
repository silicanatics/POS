package com.nkd.pos.posapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Entities")
public class DBEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	private String name;
	private String surname;
	private String initials;
	@ManyToOne
	@JoinColumn(name = "entityTypeId")
	private EntityType entityType;
	public DBEntity() {
		super();
	}

	public DBEntity(int id, String name, String surname, String initials, EntityType entityType) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.initials = initials;
		this.entityType = entityType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public EntityType getEntityType() {
		return entityType;
	}
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	
	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	@Override
	public String toString() {
		return "DBEntity [id=" + id + ", name=" + name + ", surname=" + surname + ", initials=" + initials
				+ ", entityType=" + entityType + "]";
	}

	
}
