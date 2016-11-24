package com.sotouch.myway.view.action.softwareUpdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class SoftwareUpdateViewBean extends BaseViewBean {
	
	private String updateTime;
	private Integer licenseId;
	private String licenseName;
	private String projectKey;
	private String projectName;
	private String pcName;
	private String ip;
	private String oldVersion;
	private String newVersion;
	
	private String logType;
	private String description;
	
	private String search;

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}
	
	public Integer getLicenseId() {
		return licenseId;
	}
	
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	
	public String getLicenseName() {
		return licenseName;
	}
	
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	
	public String getProjectKey() {
		return projectKey;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	
	public String getPcName() {
		return pcName;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}
	
	public String getOldVersion() {
		return oldVersion;
	}
	
	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}
	
	public String getNewVersion() {
		return newVersion;
	}
	
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getLogType() {
		return logType;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getSearch() {
		return search;
	}
	
	//softwareUpdate.erreur.key.obligatoire=\u9879\u76EE\u7F16\u53F7\u4E0D\u80FD\u4E3A\u7A7A
	//		softwareUpdate.erreur.oldVersion.obligatoire=\u5347\u7EA7\u524D\u7248\u672C\u4E0D\u80FD\u4E3A\u7A7A
	//		softwareUpdate.erreur.newVersion.obligatoire=\u5347\u7EA7\u672C\u7248\u4E0D\u80FD\u4E3A\u7A7A

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (projectKey == null) {
			errors.add(new JSONError("viewBean.projectKey", resources.getString("softwareUpdate.erreur.key.obligatoire")));
		}
		
		if (licenseId == null) {
			errors.add(new JSONError("viewBean.licenseId", resources.getString("softwareUpdate.erreur.license.obligatoire")));
		}
		
		if (StringUtil.isEmpty(oldVersion)) {
			errors.add(new JSONError("viewBean.oldVersion", resources.getString("softwareUpdate.erreur.oldVersion.obligatoire")));
		}
		
		if (StringUtil.isEmpty(newVersion)) {
			errors.add(new JSONError("viewBean.newVersion", resources.getString("softwareUpdate.erreur.newVersion.obligatoire")));
		}
		
		if (StringUtil.isEmpty(logType)) {
			errors.add(new JSONError("viewBean.logType", resources.getString("softwareUpdate.erreur.logType.obligatoire")));
		}
		
		return errors;
	}
}
