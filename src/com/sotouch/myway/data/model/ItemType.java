package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class ItemType extends BaseDataBean {

	private String name;
	private String icon;
	private Integer index = 0;

	public ItemType() {
		super();
	}

	public ItemType(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
}
