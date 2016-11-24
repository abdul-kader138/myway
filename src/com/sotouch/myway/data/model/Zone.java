package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Zone extends BaseDataBean {

	private String name;
	private String color;
	private Building building;
	private int transparency;
	private String image;
	
	private Boolean itemsInZone = Boolean.FALSE;
	
	private Category category;
	private SubCategory subCategory;
	
	private List<ZoneContent> zoneCogntents = new ArrayList<ZoneContent>();
	private Set<Item> items = new LinkedHashSet<Item>();

	public Zone() {
		super();
	}

	public Zone(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getTransparency() {
		return this.transparency;
	}
	public void setTransparency(Integer transparency) {
		this.transparency = transparency;
	}
	
	public Building getBuilding() {
		return this.building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}

	public List<ZoneContent> getZoneContents() {
		return zoneCogntents;
	}
	public void setZoneContents(List<ZoneContent> zoneCogntents) {
		this.zoneCogntents = zoneCogntents;
	}

	
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return this.subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public Boolean getItemsInZone() {
		return this.itemsInZone;
	}
	public void setItemsInZone(Boolean itemsInZone) {
		this.itemsInZone = itemsInZone;
	}
}
