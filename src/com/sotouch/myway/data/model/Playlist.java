package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Playlist extends BaseDataBean {

	private String name;
	private Boolean active;
	private Integer index;
	private Project project;
	
	private License license;

	public Playlist() {
		super();
	}

	public Playlist(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return this.active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getIndex() {
		return this.index;
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
	
	public License getLicense() {
		return this.license;
	}
	public void setLicense(License license) {
		this.license = license;
	}

}
