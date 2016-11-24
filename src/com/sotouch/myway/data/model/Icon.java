package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Icon extends BaseDataBean {

	private String name;
	private String icon;
	private ItemType itemType;
	private IconGroup iconGroup;

	public Icon() {
		super();
	}

	public Icon(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ItemType getItemType() {
		return this.itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public IconGroup getIconGroup() {
		return iconGroup;
	}
	public void setIconGroup(IconGroup iconGroup) {
		this.iconGroup = iconGroup;
	}
}
