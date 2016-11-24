package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;
import org.hurryapp.quickstart.data.model.Utilisateur;


public class Project extends BaseDataBean {

	private String key;
	private String name;
	private String orderNumber;
	private String salesforceLink;
	private String logo;
	private String newsletterLogo;
	private String creditsImage;
	private Boolean enabled;
	private String mapConfig;
	private Date adsDateExpire;
	private Date dateExpire;
	private Date dateStart;
	private Company company;
	private Set<Utilisateur> users = new LinkedHashSet<Utilisateur>();
	private Set<License> licenses = new LinkedHashSet<License>();
	private Set<Item> items = new LinkedHashSet<Item>();
	private List<ProjectContent> projectContents = new ArrayList<ProjectContent>();

	public Project() {
		super();
	}

	public Project(Integer id) {
		super(id);
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrderNumber() {
		return this.orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getSalesforceLink() {
		return this.salesforceLink;
	}
	public void setSalesforceLink(String salesforceLink) {
		this.salesforceLink = salesforceLink;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNewsletterLogo() {
		return newsletterLogo;
	}
	public void setNewsletterLogo(String newsletterLogo) {
		this.newsletterLogo = newsletterLogo;
	}

	public String getCreditsImage() {
		return creditsImage;
	}
	public void setCreditsImage(String creditsImage) {
		this.creditsImage = creditsImage;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getMapConfig() {
		return mapConfig;
	}
	public void setMapConfig(String mapConfig) {
		this.mapConfig = mapConfig;
	}

	public Date getAdsDateExpire() {
		return adsDateExpire;
	}
	public void setAdsDateExpire(Date adsDateExpire) {
		this.adsDateExpire = adsDateExpire;
	}
	
	public Date getDateExpire() {
		return dateExpire;
	}
	public void setDateExpire(Date dateExpire) {
		this.dateExpire = dateExpire;
	}
	
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Company getCompany() {
		return this.company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Utilisateur> getUsers() {
		return users;
	}
	public void setUsers(Set<Utilisateur> users) {
		this.users = users;
	}
	
	public Set<License> getLicenses() {
		return licenses;
	}
	public void setLicenses(Set<License> licenses) {
		this.licenses = licenses;
	}

	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public List<ProjectContent> getProjectContents() {
		return projectContents;
	}
	public void setProjectContents(List<ProjectContent> projectContents) {
		this.projectContents = projectContents;
	}
}
