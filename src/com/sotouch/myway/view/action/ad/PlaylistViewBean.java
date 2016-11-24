package com.sotouch.myway.view.action.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class PlaylistViewBean extends BaseViewBean {
	
	private String name;
	private Boolean active;
	private String index;
	private String project;
	private Integer projectId;
	
	private String license;
	private Integer licenseId;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return this.active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getIndex() {
		return this.index;
	}
	public void setIndex(String index) {
		this.index = index;
	}

	public String getProject() {
		return this.project;
	}
	public void setProject(String project) {
		this.project = project;
	}

	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getLicense() {
		return this.license;
	}
	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getLicenseId() {
		return this.licenseId;
	}
	
	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("playlist.erreur.name.obligatoire")));
		}

		return errors;
	}
	
}
