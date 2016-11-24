package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Language extends BaseDataBean {

	private String name;
	private String code;
	private String flag;
	private Integer index;
	private Boolean defaultLanguage;
	private Project project;

	public Language() {
		super();
	}

	public Language(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getFlag() {
		return this.flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Boolean getDefaultLanguage() {
		return defaultLanguage;
	}
	public void setDefaultLanguage(Boolean defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

}
