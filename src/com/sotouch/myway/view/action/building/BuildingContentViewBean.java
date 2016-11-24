package com.sotouch.myway.view.action.building;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;

public class BuildingContentViewBean extends BaseViewBean {
	private String name;

	private Integer languageId;
	private String languageCode;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
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
			errors.add(new JSONError("viewBean.name", resources.getString("itemContent.erreur.name.obligatoire")));
		}

		return errors;
	}
	
}
