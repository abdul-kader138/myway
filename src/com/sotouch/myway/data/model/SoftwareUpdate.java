package com.sotouch.myway.data.model;

import java.util.Date;

import org.hurryapp.fwk.data.model.BaseDataBean;
import org.hurryapp.quickstart.data.model.Software;


public class SoftwareUpdate extends BaseDataBean {
	
	private Date updateTime;
	private License license;
	private Project project;
	private String pcName;
	private String ip;
	private String oldVersion;
	private String newVersion;
	
	private String logType;
	private String description;

	
	public SoftwareUpdate() {
		super();
	}

	public SoftwareUpdate(Integer id) {
		super(id);
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}
	
	public License getLicense() {
		return license;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return project;
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
}
