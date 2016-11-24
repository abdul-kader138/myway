package com.sotouch.myway.view.action.activation;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.view.action.BaseAction;

import com.sotouch.myway.service.activation.ActivationService;
import com.sotouch.myway.view.action.item.ItemViewBean;

@Unsecured
public class ActivationAction extends BaseAction<ItemViewBean> {

	private String company_id;
	private String company_name;
	private String project_names;
	private String name;
	private String names;
	private String surname;
	private String descriptions;
	private String project_key;
	private String expiration_date;

	private String xml;

	@Resource (name="activationService")
	private ActivationService activationService;

	@Override
	protected void doExecute() throws Exception {
	}

	@Override
	protected void doInit() throws Exception {
	}

	/**
	 * Create projects for a company
	 * url = /activation/createProjects?company_id=xxx&company_name=yyy&name=nnn&surname=sss&project_names=ppp
	 * @return
	 * @throws Exception
	 */
	public String createProjects() throws Exception {
		xml = activationService.createProjects(company_id, company_name, name, surname, project_names);
		setXmlResponse(xml);
		return SUCCESS_XML;
	}

	/**
	 * Create terminals for a project
	 * url = /activation/createTerminals?project_key=xxx&names=nnn&descriptions=ddd
	 * @return
	 * @throws Exception
	 */
	public String createTerminals() throws Exception {
		xml = activationService.createTerminals(project_key, names, descriptions);
		setXmlResponse(xml);
		return SUCCESS_XML;
	}

	/**
	 * Activate the ads for a project
	 * url = /activation/activateAds?project_key=xxx&expiration_date=yyyy-MM-dd
	 * @return
	 * @throws Exception
	 */
	public String activateAds() throws Exception {
		xml = activationService.activateAds(project_key, expiration_date);
		setXmlResponse(xml);
		return SUCCESS_XML;
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getProject_names() {
		return project_names;
	}
	public void setProject_names(String projectNames) {
		project_names = projectNames;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getProject_key() {
		return project_key;
	}
	public void setProject_key(String project_key) {
		this.project_key = project_key;
	}

	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
}
