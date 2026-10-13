package com.nkd.pos.posapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DeliveryCharge")
public class DeliveryCharge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	private String distance;
	private float charge;
	public DeliveryCharge() {
		super();
	}
	public DeliveryCharge(int id, String distance, float charge) {
		super();
		this.id = id;
		this.distance = distance;
		this.charge = charge;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public float getCharge() {
		return charge;
	}
	public void setCharge(float charge) {
		this.charge = charge;
	}
	@Override
	public String toString() {
		return "DeliveryCharge [id=" + id + ", distance=" + distance + ", charge=" + charge + "]";
	}

	

}
