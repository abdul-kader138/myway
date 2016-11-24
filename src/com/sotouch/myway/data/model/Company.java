package com.sotouch.myway.data.model;

import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;
import org.hurryapp.quickstart.data.model.Utilisateur;


public class Company extends BaseDataBean {

	private String idStr;
	private String name;
	private String address;
	private String city;
	private String country;
	private Boolean enabled = Boolean.TRUE;
	private Set<Utilisateur> users;

	public Company() {
		super();
	}

	public Company(Integer id) {
		super(id);
	}

	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Utilisateur> getUsers() {
		return users;
	}
	public void setUsers(Set<Utilisateur> users) {
		this.users = users;
	}
}
