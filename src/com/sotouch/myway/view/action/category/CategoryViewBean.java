package com.sotouch.myway.view.action.category;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class CategoryViewBean extends BaseViewBean {
	
	private String name;
	private String color;
	private String project;
	private Integer projectId;
	private List<CategoryContentViewBean> categoryContents = new ArrayList<CategoryContentViewBean>();

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

	public String getProject() {
		return this.project;
	}
	public void setProject(String project) {
		this.project = project;
	}

	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public List<CategoryContentViewBean> getCategoryContents() {
		return categoryContents;
	}
	public void setCategoryContents(List<CategoryContentViewBean> categoryContents) {
		this.categoryContents = categoryContents;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(color)) {
			errors.add(new JSONError("viewBean.color", resources.getString("categoryContent.erreur.color.obligatoire")));
		}
		
		int i = 0;
		for (CategoryContentViewBean categoryContentViewBean : categoryContents) {
			if (StringUtil.isEmpty(categoryContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.categoryContents["+i+"].name", resources.getString("categoryContent.erreur.name.obligatoire")));
			}
			i++;
		}

		return errors;
	}
}
