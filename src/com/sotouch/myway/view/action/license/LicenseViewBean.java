package com.sotouch.myway.view.action.license;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.view.action.item.ItemContentViewBean;

public class LicenseViewBean extends BaseViewBean {
	
	private String key;
	private String description;
	private String orientation;
	private Integer projectId;
	private Integer itemId;
	private String deviceIds;
	private String logo;
	private String visual;
	
	private List<LicenseContentViewBean> licenseContents;
	
	private String logoContentType;
	private String logoFileName;
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLogo() {
		return this.logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getVisual() {
		return this.visual;
	}
	public void setVisual(String visual) {
		this.visual = visual;
	}
	
	public String getLogoContentType() {
		return this.logoContentType;
	}
	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}
	
	public String getLogoFileName() {
		return this.logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getItemId() {
		return this.itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public String getDeviceIds() {
		return deviceIds;
	}
	
	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}
	
	public List<LicenseContentViewBean> getLicenseContents() {
		return licenseContents;
	}
	
	public void setLicenseContents(List<LicenseContentViewBean> licenseContents) {
		this.licenseContents = licenseContents;
	}
	
	//-----------------------------------------------------------------------------
	// Champs bis (pour le panel formulaire)
	//-----------------------------------------------------------------------------
	
	public String getKeyBis() {
		return this.key;
	}
	public void setKeyBis(String key) {
		this.key = key;
	}

	public String getDescriptionBis() {
		return this.description;
	}
	public void setDescriptionBis(String description) {
		this.description = description;
	}

	public String getOrientationBis() {
		return orientation;
	}
	public void setOrientationBis(String orientation) {
		this.orientation = orientation;
	}

	public Integer getProjectIdBis() {
		return this.projectId;
	}
	public void setProjectIdBis(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getItemIdBis() {
		return this.itemId;
	}
	public void setItemIdBis(Integer itemId) {
		this.itemId = itemId;
	}


	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (id == null && StringUtil.isEmpty(logo)) {
			errors.add(new JSONError("viewBean.logo", resources.getString("license.erreur.logo.obligatoire")));
		}

		int i = 0;
		for (LicenseContentViewBean licenseContentViewBean : licenseContents) {
			if (StringUtil.isEmpty(licenseContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.licenseContents["+i+"].name", resources.getString("licenseContent.erreur.name.obligatoire")));
			}
			i++;
		}
		
		return errors;
	}
	
}
