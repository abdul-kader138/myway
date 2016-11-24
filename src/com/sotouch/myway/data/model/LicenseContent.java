package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class LicenseContent extends BaseDataBean {

	private Integer index;
	private String name;
	private String description;
	private License license;
	private Language language;

	public LicenseContent() {
		super();
	}

	public LicenseContent(Integer id) {
		super(id);
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public License getLicense() {
		return license;
	}
	public void setLicense(License license) {
		this.license = license;
	}

	public Language getLanguage() {
		return this.language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

}
