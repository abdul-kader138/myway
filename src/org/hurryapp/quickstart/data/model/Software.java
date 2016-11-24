package org.hurryapp.quickstart.data.model;

import java.util.Date;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Software extends BaseDataBean {
	
	private String vendor;
	private String version;
	private String installPack;
	private Date uploadTime;
	private Date modifyTime;
	private Utilisateur user;

	public Software() {
		super();
	}

	public Software(Integer id) {
		super(id);
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getVendor() {
		return vendor;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setInstallPack(String installPack) {
		this.installPack = installPack;
	}
	
	public String getInstallPack() {
		return installPack;
	}
	
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public Date getUploadTime() {
		return uploadTime;
	}
	
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}
	
	public void setUser(Utilisateur user) {
		this.user = user;
	}
	
	public Utilisateur getUser() {
		return user;
	}
}
