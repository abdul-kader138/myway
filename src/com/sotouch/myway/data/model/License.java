package com.sotouch.myway.data.model;

import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;
import org.hurryapp.quickstart.data.model.Device;


public class License extends BaseDataBean {

	private String key;
	private String description;
	private String orientation;
	private String logo;
	private Project project;
	private Item item;
	private Set<Device> devices;
	private List<LicenseContent> licenseContents;

	public License() {
		super();
	}

	public License(Integer id) {
		super(id);
	}

	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getLogo() {
		return this.logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public Item getItem() {
		return this.item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Set<Device> getDevices() {
		return this.devices;
	}
	
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public List<LicenseContent> getLicenseContents() {
		return licenseContents;
	}
	public void setLicenseContents(List<LicenseContent> licenseContents) {
		this.licenseContents = licenseContents;
	}
	
}
