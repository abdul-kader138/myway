package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class ProjectContent extends BaseDataBean {

	private String newsletterTerms;
	private Integer index;
	private Project project;
	private Language language;

	public ProjectContent() {
		super();
	}

	public ProjectContent(Integer id) {
		super(id);
	}

	public String getNewsletterTerms() {
		return newsletterTerms;
	}
	public void setNewsletterTerms(String newsletterTerms) {
		this.newsletterTerms = newsletterTerms;
	}

	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
}
