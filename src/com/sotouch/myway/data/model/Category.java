package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Category extends BaseDataBean {

	private String name;
	private String color;
	private Project project;
	private List<CategoryContent> categoryContents = new ArrayList<CategoryContent>();
	private Set<SubCategory> subCategories = new LinkedHashSet<SubCategory>();

	public Category() {
		super();
	}

	public Category(Integer id) {
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

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public List<CategoryContent> getCategoryContents() {
		return categoryContents;
	}
	public void setCategoryContents(List<CategoryContent> categoryContents) {
		this.categoryContents = categoryContents;
	}

	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}
}
