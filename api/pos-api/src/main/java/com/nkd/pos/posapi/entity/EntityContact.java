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
@Table(name="EntityContacts")
public class EntityContact implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name="entityId")
	private DBEntity dbEntity;
	@ManyToOne
	@JoinColumn(name="contactTypeId")
	private ContactType contactType;
	private String contact;
	public EntityContact() {
		super();
	}
	public EntityContact(int id, DBEntity dbEntity, ContactType contactType, String contact) {
		super();
		this.id = id;
		this.dbEntity = dbEntity;
		this.contactType = contactType;
		this.contact = contact;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public DBEntity getDbEntity() {
		return dbEntity;
	}
	public void setDbEntity(DBEntity dbEntity) {
		this.dbEntity = dbEntity;
	}
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "EntityContact [id=" + id + ", dbEntity=" + dbEntity + ", contactType=" + contactType + ", contact="
				+ contact + "]";
	}
	
}
