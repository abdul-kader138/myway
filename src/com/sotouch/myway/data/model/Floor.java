package com.sotouch.myway.data.model;

import java.util.List;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Floor extends BaseDataBean {

	private String name;
	private String label;
	private String image;
	private Integer index;
	private Building building;
	private List<FloorContent> floorContents;

	public Floor() {
		super();
	}

	public Floor(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public Integer getIndex() {
		return this.index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Building getBuilding() {
		return this.building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}

	public List<FloorContent> getFloorContents() {
		return floorContents;
	}
	public void setFloorContents(List<FloorContent> floorContents) {
		this.floorContents = floorContents;
	}
}
