package com.sotouch.myway.view.action.newsletterEmail;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class NewsletterEmailViewBean extends BaseViewBean {
	
	private String name;
	private String firstName;
	private String email;
	private String city;
	private String genderId;
	private String gender;
	private String categories;
	private String items;
	private Integer projectId;
	
	private Integer licenseId;
	private String licenseName;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getGenderId() {
		return genderId;
	}
	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCategories() {
		return this.categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getItems() {
		return this.items;
	}
	public void setItems(String items) {
		this.items = items;
	}

	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getLicenseId() {
		return this.licenseId;
	}
	
	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}
	
	public String getLicenseName() {
		return this.licenseName;
	}
	
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(email)) {
			errors.add(new JSONError("viewBean.email", resources.getString("newsletterEmail.erreur.email.obligatoire")));
		}

		return errors;
	}
	
}
