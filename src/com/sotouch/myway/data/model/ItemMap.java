package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class ItemMap extends BaseDataBean {

	private Integer x;
	private Integer y;
	private Integer linkId;
	private String closestIds;
	private Item item;
	private Floor floor;

	public ItemMap() {
		super();
	}

	public ItemMap(Integer id) {
		super(id);
	}

	public Integer getX() {
		return this.x;
	}
	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return this.y;
	}
	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getLinkId() {
		return linkId;
	}
	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getClosestIds() {
		return closestIds;
	}
	public void setClosestIds(String closestIds) {
		this.closestIds = closestIds;
	}

	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

	public Floor getFloor() {
		return this.floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}

}
