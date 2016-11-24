package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class SubCategoryContent extends BaseDataBean {

	private String name;
	private Integer index;
	private SubCategory subCategory;
	private Language language;

	public SubCategoryContent() {
		super();
	}

	public SubCategoryContent(Integer id) {
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

	public SubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Language getLanguage() {
		return this.language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

}
