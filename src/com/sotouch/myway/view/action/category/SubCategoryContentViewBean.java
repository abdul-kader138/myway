package com.sotouch.myway.view.action.category;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class SubCategoryContentViewBean extends BaseViewBean {
	
	private String name;
	private String description;
	private String keywords;
	private Integer languageId;
	private String languageCode;

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

	public String getKeywords() {
		return this.keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getLanguageId() {
		return this.languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("sbCategoryContent.erreur.name.obligatoire")));
		}

		return errors;
	}
	
}
