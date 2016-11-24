package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.List;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Building extends BaseDataBean {

	private String name;
	private Integer index;
	private Project project;
	private List<Floor> floors = new ArrayList<Floor>();
	private List<BuildingContent> buildingContents;

	public Building() {
		super();
	}

	public Building(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public List<Floor> getFloors() {
		return floors;
	}
	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}
	
	public List<BuildingContent> getBuildingContents() {
		return buildingContents;
	}
	public void setBuildingContents(List<BuildingContent> buildingContents) {
		this.buildingContents = buildingContents;
	}
}
