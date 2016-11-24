package org.hurryapp.quickstart.view.action.software;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.data.model.Utilisateur;

public class SoftwareViewBean extends BaseViewBean {
	
	private String vendor;
	private String version;
	private String installPack;
	private String uploadTime;
	private String modifyTime;
	private Utilisateur user;
	private String userName;
	
	private String installPackContentType;
	private String installPackFileName;

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
	
	public void setInstallPackFileName(String installPackFileName) {
		this.installPackFileName = installPackFileName;
	}
	
	public String getInstallPackFileName() {
		return installPackFileName;
	}
	
	public void setInstallPackContentType(String installPackContentType) {
		this.installPackContentType = installPackContentType;
	}
	
	public String getInstallPackContentType() {
		return installPackContentType;
	}
	
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getUploadTime() {
		return uploadTime;
	}
	
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getModifyTime() {
		return modifyTime;
	}
	
	public void setUser(Utilisateur user) {
		this.user = user;
	}
	
	public Utilisateur getUser() {
		return user;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(version)) {
			errors.add(new JSONError("viewBean.version", resources.getString("software.erreur.version.obligatoire")));
		}
		
		if (StringUtil.isEmpty(installPack)) {
			errors.add(new JSONError("viewBean.installPack", resources.getString("software.erreur.installPack.obligatoire")));
		}
		
		return errors;
	}
}
