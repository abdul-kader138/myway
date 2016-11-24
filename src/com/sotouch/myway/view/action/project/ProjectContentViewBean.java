package com.sotouch.myway.view.action.project;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class ProjectContentViewBean extends BaseViewBean {
	
	private String newsletterTerms;
	private Integer languageId;
	private String languageCode;

	public String getNewsletterTerms() {
		return newsletterTerms;
	}
	public void setNewsletterTerms(String newsletterTerms) {
		this.newsletterTerms = newsletterTerms;
	}

	public Integer getLanguageId() {
		return languageId;
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
		
		return errors;
	}
	
}
