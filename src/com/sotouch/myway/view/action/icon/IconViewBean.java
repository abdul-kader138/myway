package com.sotouch.myway.view.action.icon;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class IconViewBean extends BaseViewBean {
	
	private String name;
	private String icon;
	private String iconContentType;
	private String iconFileName;
	private Integer itemTypeId;
	private Integer iconGroupId;
	private String timestamp;
	private String subFolder;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getSubFolder() {
		return subFolder;
	}
	public void setSubFolder(String subFolder) {
		this.subFolder = subFolder;
	}

	public String getIconContentType() {
		return iconContentType;
	}
	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}

	public String getIconFileName() {
		return iconFileName;
	}
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}
	
	public Integer getItemTypeId() {
		return this.itemTypeId;
	}
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public Integer getIconGroupId() {
		return iconGroupId;
	}
	public void setIconGroupId(Integer iconGroupId) {
		this.iconGroupId = iconGroupId;
	}

	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("icon.erreur.name.obligatoire")));
		}

		if (id == null && StringUtil.isEmpty(icon)) {
			errors.add(new JSONError("viewBean.icon", resources.getString("icon.erreur.icon.obligatoire")));
		}
		if (iconFileName != null && !iconFileName.toLowerCase().endsWith(".png") && !iconFileName.toLowerCase().endsWith(".gif") && !iconFileName.toLowerCase().endsWith(".jpg")) {
			errors.add(new JSONError("viewBean.icon", resources.getString("icon.erreur.icon.image")));
		}

		return errors;
	}
	
}
