package com.sotouch.myway.view.action.category;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class SubCategoryViewBean extends BaseViewBean {
	
	private String name;
	private String color;
	private String category;
	private Integer categoryId;
	private List<SubCategoryContentViewBean> subCategoryContents = new ArrayList<SubCategoryContentViewBean>();

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

	public String getCategory() {
		return this.category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<SubCategoryContentViewBean> getSubCategoryContents() {
		return subCategoryContents;
	}
	public void setSubCategoryContents(List<SubCategoryContentViewBean> subCategoryContents) {
		this.subCategoryContents = subCategoryContents;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		int i = 0;
		for (SubCategoryContentViewBean subCategoryContentViewBean : subCategoryContents) {
			if (StringUtil.isEmpty(subCategoryContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.subCategoryContents["+i+"].name", resources.getString("subCategoryContent.erreur.name.obligatoire")));
			}
			i++;
		}

		return errors;
	}
	
}
