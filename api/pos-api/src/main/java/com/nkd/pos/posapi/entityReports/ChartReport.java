package com.nkd.pos.posapi.entityReports;

import com.nkd.pos.posapi.entity.MenuItem;

public class ChartReport {

	MenuItem mi;
	float value;
	String description;
	public ChartReport() {
		super();
	}

	public ChartReport(MenuItem mi, float value, String description) {
		super();
		this.mi = mi;
		this.value = value;
		this.description = description;
	}

	public MenuItem getMi() {
		return mi;
	}

	public void setMi(MenuItem mi) {
		this.mi = mi;
	}

	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ChartReport [mi=" + mi + ", value=" + value + ", description=" + description + "]";
	}
	
	
}
