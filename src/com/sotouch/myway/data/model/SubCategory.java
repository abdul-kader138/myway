package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.List;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class SubCategory extends BaseDataBean {

	private String name;
	private String color;
	private Category category;
	private List<SubCategoryContent> subCategoryContents = new ArrayList<SubCategoryContent>();

	public SubCategory() {
		super();
	}

	public SubCategory(Integer id) {
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

	public Category getCategory() {
		return this.category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public List<SubCategoryContent> getSubCategoryContents() {
		return subCategoryContents;
	}
	public void setSubCategoryContents(List<SubCategoryContent> subCategoryContents) {
		this.subCategoryContents = subCategoryContents;
	}
}
