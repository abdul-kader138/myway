package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class NewsletterEmail extends BaseDataBean {

	private String name;
	private String firstName;
	private String email;
	private String city;
	private String gender;
	private String categories;
	private String items;
	private Project project;
	private License license;
	

	public NewsletterEmail() {
		super();
	}

	public NewsletterEmail(Integer id) {
		super(id);
	}

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

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	public License getLicense() {
		return this.license;
	}
	public void setLicense(License license) {
		this.license = license;
	}

}
