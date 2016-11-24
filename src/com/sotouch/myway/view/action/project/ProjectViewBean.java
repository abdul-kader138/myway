package com.sotouch.myway.view.action.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class ProjectViewBean extends BaseViewBean {
	
	private String key;
	private String name;
	private String orderNumber;
	private String salesforceLink;
	private String company;
	private String dateStart;
	private String dateExpire;
	private String adsDateExpire;
	private Integer companyId;
	private String userIds;
	private String logo;
	private String logoContentType;
	private String logoFileName;
	private String newsletterLogo;
	private String newsletterLogoContentType;
	private String newsletterLogoFileName;
	private String creditsImage;
	private String creditsImageContentType;
	private String creditsImageFileName;
	private List<ProjectContentViewBean> projectContents = new ArrayList<ProjectContentViewBean>();

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

	public String getCompany() {
		return this.company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getDateExpire() {
		return dateExpire;
	}
	public void setDateExpire(String dateExpire) {
		this.dateExpire = dateExpire;
	}
	
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	
	public String getAdsDateExpire() {
		return adsDateExpire;
	}
	public void setAdsDateExpire(String adsDateExpire) {
		this.adsDateExpire = adsDateExpire;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoContentType() {
		return logoContentType;
	}
	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}
	
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	public String getNewsletterLogo() {
		return newsletterLogo;
	}
	public void setNewsletterLogo(String newsletterLogo) {
		this.newsletterLogo = newsletterLogo;
	}
	
	public String getNewsletterLogoContentType() {
		return newsletterLogoContentType;
	}
	public void setNewsletterLogoContentType(String newsletterLogoContentType) {
		this.newsletterLogoContentType = newsletterLogoContentType;
	}
	
	public String getNewsletterLogoFileName() {
		return newsletterLogoFileName;
	}
	public void setNewsletterLogoFileName(String newsletterLogoFileName) {
		this.newsletterLogoFileName = newsletterLogoFileName;
	}
	
	public String getCreditsImage() {
		return creditsImage;
	}
	public void setCreditsImage(String creditsImage) {
		this.creditsImage = creditsImage;
	}
	
	public String getCreditsImageContentType() {
		return creditsImageContentType;
	}
	public void setCreditsImageContentType(String creditsImageContentType) {
		this.creditsImageContentType = creditsImageContentType;
	}
	
	public String getCreditsImageFileName() {
		return creditsImageFileName;
	}
	public void setCreditsImageFileName(String creditsImageFileName) {
		this.creditsImageFileName = creditsImageFileName;
	}
	
	public List<ProjectContentViewBean> getProjectContents() {
		return projectContents;
	}
	public void setProjectContents(List<ProjectContentViewBean> projectContents) {
		this.projectContents = projectContents;
	}
	
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("project.erreur.name.obligatoire")));
		}

		if (companyId == null) {
			errors.add(new JSONError("viewBean.companyId", resources.getString("project.erreur.company.obligatoire")));
		}

		if (logoFileName != null && !logoFileName.toLowerCase().endsWith(".png") && !logoFileName.toLowerCase().endsWith(".gif") && !logoFileName.toLowerCase().endsWith(".jpg")) {
			errors.add(new JSONError("viewBean.logo", resources.getString("project.erreur.logo.format")));
		}
		return errors;
	}
	
}
